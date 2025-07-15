package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Item;
import gift.entity.Member;
import gift.entity.Role;
import gift.entity.Wish;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Member testMember;
    private Item testItem;

    @BeforeEach
    void setUp() {
        testMember = memberRepository.save(new Member(null, "test@example.com", "123", Role.USER));
        testItem = itemRepository.save(new Item(null, "test item", 100, "test.jpg"));
    }

    @Test
    @DisplayName("회원으로 위시리스트 조회 (Fetch Join 적용)")
    void findByMemberWithProduct() {
        wishRepository.save(new Wish(testMember, testItem, 1));

        List<Wish> wishes = wishRepository.findAllByMemberWithProduct(testMember);

        assertThat(wishes).hasSize(1);
        assertThat(wishes.get(0).getProduct().getName()).isEqualTo("test item");
    }
}