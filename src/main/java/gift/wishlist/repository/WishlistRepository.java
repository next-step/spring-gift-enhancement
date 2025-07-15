package gift.wishlist.repository;

import gift.entity.WishlistItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository {
    int addWishlistItem(WishlistItem item);

    Optional<WishlistItem> findWishlistById(Long id);

    List<WishlistItem> findAllWishlistItemsByMemberId(Long memberId);

    int updateWishlistItemById(Long id, Long quantity);

    int deleteById(Long id);

    Optional<WishlistItem> findProductInMemberById(Long memberId, Long productId);
}
