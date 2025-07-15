package gift.jpa;

import gift.dto.itemDto.ItemCreateDto;
import gift.dto.itemDto.ItemUpdateDto;
import gift.entity.Item;
import gift.repository.itemRepository.ItemRepositoryJPA;
import gift.service.itemService.ItemService;
import gift.service.itemService.ItemServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepositoryJPA itemRepository;

    @Autowired
    private TestEntityManager em;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceJpa(itemRepository);
    }

    @Test
    void 아이템저장성공() {
        ItemCreateDto dto = new ItemCreateDto("초콜릿", 1500, "/img/choco.png",false);

        Item saved = itemService.saveItem(dto);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("초콜릿");
        assertThat(saved.getPrice()).isEqualTo(1500);
        assertThat(saved.getImageUrl()).isEqualTo("/img/choco.png");
    }

    @Test
    void 이름과가격으로아이템조회() {
        em.persist(new Item("초콜릿", 1500, "/img/choco.png"));
        em.persist(new Item("케이크", 3000, "/img/cake.png"));

        List<Item> items = itemService.getItems("케이크", 3000);

        assertThat(items).hasSize(1);
        assertThat(items.get(0).getName()).isEqualTo("케이크");
    }

    @Test
    void 아이템삭제() {
        em.persist(new Item("쿠키", 1000, "/img/cookie.png"));

        itemService.delete("쿠키");

        List<Item> allItems = itemService.getAllItems();
        assertThat(allItems).isEmpty();
    }

    @Test
    void 아이템수정() {
        Item item = em.persist(new Item("마카롱", 2000, "/img/macaron.png"));

        ItemUpdateDto dto = new ItemUpdateDto(null, "업데이트", 2500, "/img/update.png",false);

        Item updated = itemService.updateItem(item.getId(), dto);

        assertThat(updated.getName()).isEqualTo("업데이트");
        assertThat(updated.getPrice()).isEqualTo(2500);
        assertThat(updated.getImageUrl()).isEqualTo("/img/update.png");
    }

    @Test
    void ID로아이템조회() {
        Item item = em.persist(new Item("롤케이크", 4000, "/img/roll.png"));

        Optional<Item> found = itemService.findItemById(item.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("롤케이크");
    }

    @Test
    void 모든아이템조회() {
        em.persist(new Item("a", 100, "a.png"));
        em.persist(new Item("b", 200, "b.png"));

        List<Item> all = itemService.getAllItems();

        assertThat(all).hasSize(2);
    }
}