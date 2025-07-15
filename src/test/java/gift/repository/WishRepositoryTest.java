package gift.repository;

import gift.dto.WishRequestDto;
import gift.entity.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Test
    void save() {
        WishRequestDto wishRequestDto = new WishRequestDto(1L, 3L);
        Wish expected = new Wish(wishRequestDto);
        Wish actual = wishRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId()),
                () -> assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity())
        );
    }

    @Test
    void findById() {
        Wish wish1 = wishRepository.save(new Wish(new WishRequestDto(1L, 3L)));
        Wish wish2 = wishRepository.findById(wish1.getId()).orElse(null);
        assertThat(wish1).isEqualTo(wish2);
    }

    @Test
    void existsByProductId() {
        Wish wish1 = wishRepository.save(new Wish(new WishRequestDto(1L, 3L)));
        boolean flag = wishRepository.existsByProductId(wish1.getId());
        assertThat(flag).isFalse();
    }
}
