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
    private final ProductRepository productRepository;

    public WishRestController(WishService wishService, ProductRepository productRepository) {
        this.wishService = wishService;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<CreateWishResponse> addWish(@Authenticated Member member,
            @RequestBody CreateWishRequest request) {
        Product product = productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        wishService.addWish(member, product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWish(@Authenticated Member member,
            @RequestParam Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        wishService.removeWish(member, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getMyWishes(@Authenticated Member member) {
        List<Product> wishes = wishService.getAllWish(member);
        return ResponseEntity.ok(wishes);
    }

}
