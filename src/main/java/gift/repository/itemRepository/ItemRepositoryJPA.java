package gift.repository.itemRepository;

import gift.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepositoryJPA extends JpaRepository<Item, Long> {

    Item findByName(String name);

    List<Item> findByPrice(Integer price);

    List<Item> findByNameAndPrice(String name, Integer price);
}
