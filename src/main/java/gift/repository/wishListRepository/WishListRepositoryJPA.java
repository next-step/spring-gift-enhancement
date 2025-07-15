package gift.repository.wishListRepository;

import gift.entity.User;
import gift.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepositoryJPA extends JpaRepository<WishItem, Long> {
    List<WishItem> findAllByUser(User user);
}
