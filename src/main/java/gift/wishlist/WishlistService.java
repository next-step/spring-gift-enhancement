package gift.wishlist;

import gift.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {
    private final WishRepository wishRepository;

    public WishlistService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Transactional
    public List<Wishlist> getWishlistById(Long userId) {
        return wishRepository.findByUserId(userId);
    }

    @Transactional
    public Wishlist createWishlist(User user, WishlistSaveRequestDto wishlistSaveRequestDto) {
        Wishlist wishlist = new Wishlist(user, wishlistSaveRequestDto.getProduct());
        return wishRepository.save(wishlist);
    }

    @Transactional
    public void deleteWishlist(Long id) {
        Wishlist wishlist = wishRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 ID가 존재하지 않습니다."));
        wishRepository.delete(wishlist);
    }
}
