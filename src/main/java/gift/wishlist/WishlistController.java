package gift.wishlist;

import gift.auth.LoginUser;
import gift.product.dto.ResponseDto;
import gift.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Wishlist>> getWishlist(@LoginUser User user) {
        List<Wishlist> wishlist = wishlistService.getWishlistById(user.getId());
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping("/page")
    public ResponseEntity<List<Wishlist>> findAllByPage(
            @LoginUser User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        List<Wishlist> responseDtoList = wishlistService.getWishlistByIdAndPage(user.getId(), page, size, sortBy, sortOrder)
                .stream()
                .toList();
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addWishlist(@LoginUser User user, @RequestBody WishlistSaveRequestDto wishlistSaveRequestDto) {
        Wishlist wishlist =  wishlistService.createWishlist(user, wishlistSaveRequestDto);
        return ResponseEntity
                .created(URI.create("/api/wishlist/" + wishlist.getId()))
                .body(wishlist);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteById(@LoginUser User user, @PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }
}
