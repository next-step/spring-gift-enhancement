package gift.wishlist.service;

import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.shared.exception.product.NoProductException;
import gift.shared.exception.user.NoUserException;
import gift.user.entity.User;
import gift.user.repository.UserRepository;
import gift.wishlist.dto.response.WishlistResponse;
import gift.wishlist.entity.Wishlist;
import gift.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<WishlistResponse> getWishlists(Long userId){
        return wishlistRepository.findAllByUserId(userId)
                .stream()
                .map(WishlistResponse::from)
                .toList();
    }

    public void addWishList(Long giftId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserException("No user found with id: " + userId));
        Product product = productRepository.findById(giftId)
                .orElseThrow(() -> new NoProductException("No gift found with id: " + giftId));
        wishlistRepository.save(new Wishlist(user, product));
    }

    public void deleteById(Long wishlistId){
        wishlistRepository.deleteById(wishlistId);
    }
}
