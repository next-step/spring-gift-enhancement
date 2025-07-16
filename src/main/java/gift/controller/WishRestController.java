package gift.controller;

import gift.dto.CreateWishRequest;
import gift.dto.CreateWishResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.jwt.Authenticated;
import gift.repository.ProductRepository;
import gift.service.WishService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishRestController {

    private final WishService wishService;

    public WishRestController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<CreateWishResponse> addWish(@Authenticated Member member,
            @RequestBody CreateWishRequest request) {
        wishService.addWish(member, request.getProductId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWish(@Authenticated Member member,
            @RequestParam Long productId) {
        wishService.removeWish(member, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getMyWishes(@Authenticated Member member) {
        List<Product> wishes = wishService.getAllWish(member);
        return ResponseEntity.ok(wishes);
    }
}