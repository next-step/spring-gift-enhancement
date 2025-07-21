package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.dto.common.PageResponse;
import gift.repository.MemberJpaRepository;
import gift.repository.MemberRepositoryImpl;
import gift.repository.ProductJpaRepository;
import gift.repository.ProductRepositoryImpl;
import gift.repository.WishJpaRepository;
import gift.repository.WishRepositoryImpl;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({WishService.class, MemberRepositoryImpl.class, ProductRepositoryImpl.class, WishRepositoryImpl.class})
@DisplayName("위시 페이지네이션 테스트")
class WishPaginationTest {

    @Autowired
    private WishService wishService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private WishJpaRepository wishJpaRepository;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = memberJpaRepository.save(Member.of("test@example.com", "password"));
        
        // 10개의 테스트 상품과 위시 생성
        IntStream.range(1, 11).forEach(i -> {
            Product product = Product.of("상품" + i, 1000 * i, "http://example.com/" + i + ".jpg");
            Product savedProduct = productJpaRepository.save(product);
            
            Wish wish = Wish.of(testMember, savedProduct);
            wishJpaRepository.save(wish);
        });
    }

    @Test
    @DisplayName("위시 목록 페이지네이션 - 첫 번째 페이지")
    void getWishesFirstPage() {
        // given
        int page = 1;
        int size = 5;
        String sortBy = "id";
        String sortDirection = "DESC";

        // when
        PageResponse<WishResponse> result = wishService.getAllByMemberIdWithPagination(
                testMember.id(), page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(5),
                () -> assertThat(result.page()).isEqualTo(1),
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.totalElements()).isEqualTo(10),
                () -> assertThat(result.totalPages()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isTrue(),
                () -> assertThat(result.hasPrevious()).isFalse(),
                () -> assertThat(result.isFirst()).isTrue(),
                () -> assertThat(result.isLast()).isFalse()
        );

        // 내림차순 정렬 확인 (최근 생성된 것이 먼저)
        WishResponse firstWish = result.content().get(0);
        WishResponse secondWish = result.content().get(1);
        assertThat(firstWish.id()).isGreaterThan(secondWish.id());
    }

    @Test
    @DisplayName("위시 목록 페이지네이션 - 두 번째 페이지")
    void getWishesSecondPage() {
        // given
        int page = 2;
        int size = 5;
        String sortBy = "id";
        String sortDirection = "DESC";

        // when
        PageResponse<WishResponse> result = wishService.getAllByMemberIdWithPagination(
                testMember.id(), page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(5),
                () -> assertThat(result.page()).isEqualTo(2),
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.totalElements()).isEqualTo(10),
                () -> assertThat(result.totalPages()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isFalse(),
                () -> assertThat(result.hasPrevious()).isTrue(),
                () -> assertThat(result.isFirst()).isFalse(),
                () -> assertThat(result.isLast()).isTrue()
        );
    }

    @Test
    @DisplayName("위시 목록 Slice 페이지네이션")
    void getWishesWithSlice() {
        // given
        int page = 1;
        int size = 5;
        String sortBy = "id";
        String sortDirection = "ASC";

        // when
        PageResponse<WishResponse> result = wishService.getAllByMemberIdWithSlice(
                testMember.id(), page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(5),
                () -> assertThat(result.page()).isEqualTo(1),
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.totalElements()).isEqualTo(-1), // Slice는 total을 제공하지 않음
                () -> assertThat(result.totalPages()).isEqualTo(-1), // Slice는 totalPages를 제공하지 않음
                () -> assertThat(result.hasNext()).isTrue(),
                () -> assertThat(result.hasPrevious()).isFalse(),
                () -> assertThat(result.isFirst()).isTrue()
        );

        // 오름차순 정렬 확인
        WishResponse firstWish = result.content().get(0);
        WishResponse secondWish = result.content().get(1);
        assertThat(firstWish.id()).isLessThan(secondWish.id());
    }

    @Test
    @DisplayName("잘못된 페이지 파라미터로 예외 발생")
    void invalidPageParams() {
        assertAll(
                () -> assertThatThrownBy(() -> 
                    wishService.getAllByMemberIdWithPagination(testMember.id(), 0, 10, "id", "DESC"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("페이지 번호는 1 이상이어야 합니다"),
                
                () -> assertThatThrownBy(() -> 
                    wishService.getAllByMemberIdWithPagination(testMember.id(), 1, 0, "id", "DESC"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("페이지 크기는 1 이상 100 이하여야 합니다"),
                
                () -> assertThatThrownBy(() -> 
                    wishService.getAllByMemberIdWithPagination(testMember.id(), 1, 101, "id", "DESC"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("페이지 크기는 1 이상 100 이하여야 합니다")
        );
    }

    @Test
    @DisplayName("빈 페이지 조회")
    void getEmptyPage() {
        // given
        int page = 10; // 존재하지 않는 페이지
        int size = 5;

        // when
        PageResponse<WishResponse> result = wishService.getAllByMemberIdWithPagination(
                testMember.id(), page, size, "id", "DESC");

        // then
        assertAll(
                () -> assertThat(result.content()).isEmpty(),
                () -> assertThat(result.page()).isEqualTo(10),
                () -> assertThat(result.totalElements()).isEqualTo(10),
                () -> assertThat(result.totalPages()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isFalse(),
                () -> assertThat(result.hasPrevious()).isTrue(),
                () -> assertThat(result.isFirst()).isFalse(),
                () -> assertThat(result.isLast()).isFalse()
        );
    }
}
