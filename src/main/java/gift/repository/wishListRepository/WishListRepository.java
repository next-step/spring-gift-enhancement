package gift.repository.wishListRepository;

import gift.entity.Item;
import gift.entity.User;
import gift.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishItem, Long> {
    List<WishItem> findAllByUser(User user);

    Optional<WishItem> findByUserAndItem(User user, Item item);

    boolean existsByItem(Item item);
}
