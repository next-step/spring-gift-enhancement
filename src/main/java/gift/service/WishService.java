package gift.service;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public void addWish(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (wishRepository.existsByMemberAndProduct(member, product)) {
            throw new IllegalArgumentException("이미 위시리스트에 추가된 상품입니다.");
        }

        Wish wish = Wish.createWish(member, product);
        wishRepository.save(wish);
    }

    public void removeWish(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        wishRepository.deleteByMemberAndProduct(member, product);
    }

    public List<Product> getAllWish(Member member) {
        return wishRepository.findAllByMember(member).stream()
                .map(Wish::getProduct)
                .toList();
    }
}
