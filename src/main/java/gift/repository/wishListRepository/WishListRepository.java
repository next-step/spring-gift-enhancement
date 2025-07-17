package gift.repository.wishListRepository;

import gift.entity.Item;
import gift.entity.User;
import gift.entity.WishItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishItem, Long> {
    Page<WishItem> findAllByUser(User user, Pageable pageable);

    Optional<WishItem> findByUserAndItem(User user, Item item);

    boolean existsByItem(Item item);
}
