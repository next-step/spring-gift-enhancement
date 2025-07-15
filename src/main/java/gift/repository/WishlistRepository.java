package gift.repository;

import gift.domain.Wishlist;
import gift.dto.wishlist.WishlistResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByProductId(Long productId);

    @Query("select new gift.dto.wishlist.WishlistResponse(w) from Wishlist w where w.user.id=:userId")
    List<WishlistResponse> findAllByUserId(Long userId);
}
