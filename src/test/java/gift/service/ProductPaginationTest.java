package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Product;
import gift.dto.ProductResponse;
import gift.dto.common.PageResponse;
import gift.repository.ProductJpaRepository;
import gift.repository.ProductRepositoryImpl;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ProductManagementService.class, ProductRepositoryImpl.class})
@DisplayName("상품 페이지네이션 테스트")
class ProductPaginationTest {

    @Autowired
    private ProductManagementService productService;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @BeforeEach
    void setUp() {
        // 15개의 테스트 상품 생성
        IntStream.range(1, 16).forEach(i -> {
            Product product = Product.of("상품" + i, 1000 * i, "http://example.com/" + i + ".jpg");
            productJpaRepository.save(product);
        });
    }

    @Test
    @DisplayName("상품 목록 페이지네이션 - 가격 내림차순 정렬")
    void getProductsWithPriceSortDesc() {
        // given
        int page = 1;
        int size = 5;
        String sortBy = "price";
        String sortDirection = "DESC";

        // when
        PageResponse<ProductResponse> result = productService.getAllByPageWithSorting(
                page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(5),
                () -> assertThat(result.page()).isEqualTo(1),
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.totalElements()).isEqualTo(15),
                () -> assertThat(result.totalPages()).isEqualTo(3),
                () -> assertThat(result.hasNext()).isTrue(),
                () -> assertThat(result.hasPrevious()).isFalse(),
                () -> assertThat(result.isFirst()).isTrue(),
                () -> assertThat(result.isLast()).isFalse()
        );

        // 가격 내림차순 정렬 확인
        ProductResponse firstProduct = result.content().get(0);
        ProductResponse secondProduct = result.content().get(1);
        assertThat(firstProduct.price()).isGreaterThan(secondProduct.price());
    }

    @Test
    @DisplayName("상품 목록 페이지네이션 - 이름 오름차순 정렬")
    void getProductsWithNameSortAsc() {
        // given
        int page = 1;
        int size = 5;
        String sortBy = "name";
        String sortDirection = "ASC";

        // when
        PageResponse<ProductResponse> result = productService.getAllByPageWithSorting(
                page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(5),
                () -> assertThat(result.page()).isEqualTo(1),
                () -> assertThat(result.totalElements()).isEqualTo(15)
        );

        // 이름 오름차순 정렬 확인
        ProductResponse firstProduct = result.content().get(0);
        ProductResponse secondProduct = result.content().get(1);
        assertThat(firstProduct.name()).isLessThanOrEqualTo(secondProduct.name());
    }

    @Test
    @DisplayName("상품 목록 페이지네이션 - 마지막 페이지")
    void getProductsLastPage() {
        // given
        int page = 3; // 마지막 페이지
        int size = 5;
        String sortBy = "id";
        String sortDirection = "ASC";

        // when
        PageResponse<ProductResponse> result = productService.getAllByPageWithSorting(
                page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(5), // 15개 중 마지막 5개
                () -> assertThat(result.page()).isEqualTo(3),
                () -> assertThat(result.totalElements()).isEqualTo(15),
                () -> assertThat(result.totalPages()).isEqualTo(3),
                () -> assertThat(result.hasNext()).isFalse(),
                () -> assertThat(result.hasPrevious()).isTrue(),
                () -> assertThat(result.isFirst()).isFalse(),
                () -> assertThat(result.isLast()).isTrue()
        );
    }

    @Test
    @DisplayName("잘못된 정렬 필드로 예외 발생")
    void invalidSortField() {
        assertThatThrownBy(() -> 
            productService.getAllByPageWithSorting(1, 10, "invalidField", "ASC"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("정렬 필드는");
    }

    @Test
    @DisplayName("잘못된 페이지 파라미터로 예외 발생")
    void invalidPageParams() {
        assertAll(
                () -> assertThatThrownBy(() -> 
                    productService.getAllByPageWithSorting(0, 10, "id", "ASC"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("페이지 번호는 1 이상이어야 합니다"),
                
                () -> assertThatThrownBy(() -> 
                    productService.getAllByPageWithSorting(1, 0, "id", "ASC"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("페이지 크기는 1 이상 100 이하여야 합니다"),
                
                () -> assertThatThrownBy(() -> 
                    productService.getAllByPageWithSorting(1, 101, "id", "ASC"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("페이지 크기는 1 이상 100 이하여야 합니다")
        );
    }

    @Test
    @DisplayName("큰 페이지 크기로 모든 데이터 조회")
    void getAllProductsInOnePage() {
        // given
        int page = 1;
        int size = 20; // 전체 데이터(15개)보다 큰 크기
        String sortBy = "id";
        String sortDirection = "ASC";

        // when
        PageResponse<ProductResponse> result = productService.getAllByPageWithSorting(
                page, size, sortBy, sortDirection);

        // then
        assertAll(
                () -> assertThat(result.content()).hasSize(15), // 실제 데이터 개수
                () -> assertThat(result.page()).isEqualTo(1),
                () -> assertThat(result.totalElements()).isEqualTo(15),
                () -> assertThat(result.totalPages()).isEqualTo(1),
                () -> assertThat(result.hasNext()).isFalse(),
                () -> assertThat(result.hasPrevious()).isFalse(),
                () -> assertThat(result.isFirst()).isTrue(),
                () -> assertThat(result.isLast()).isTrue()
        );
    }
}
