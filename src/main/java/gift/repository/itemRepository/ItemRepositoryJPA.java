package gift.repository.itemRepository;

import gift.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryJPA extends JpaRepository<Item,Long> {
}
