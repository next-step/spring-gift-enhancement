package gift.repository.wishListRepository;

import gift.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepositoryJPA extends JpaRepository<WishItem, Long> {
}
