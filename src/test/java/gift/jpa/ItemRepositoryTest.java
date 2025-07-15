package gift.jpa;

import gift.entity.Item;
import gift.repository.itemRepository.ItemRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepositoryJPA repository;

    @Test
    void 저장하기() {
        // given
        Item item = new Item("케이크", 1000, "~/desktop");

        // when
        Item saved = repository.save(item);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("케이크");
        assertThat(saved.getPrice()).isEqualTo(1000);
        assertThat(saved.getImageUrl()).isEqualTo("~/desktop");
    }
}
