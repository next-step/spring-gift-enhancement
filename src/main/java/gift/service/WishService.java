package gift.service;

import gift.dto.ProductResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishService(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getWishes(Member member) {
        return member.getWishes().stream()
                .map(Product::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addWish(Member member, Long productId) {
        Product product = findProductById(productId);
        member.addWish(product);
        memberRepository.save(member);
    }

    @Transactional
    public void deleteWish(Member member, Long productId) {
        Product product = findProductById(productId);
        member.removeWish(product);
        memberRepository.save(member);
    }
    
    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + productId));
    }
}