package gift.repository.itemRepository;

import gift.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryJPA extends JpaRepository<Long, Item> {
}
