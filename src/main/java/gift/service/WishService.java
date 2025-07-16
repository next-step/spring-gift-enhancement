package gift.service;

import gift.dto.ProductResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.ProductNotFoundException;
import gift.exception.WishAlreadyExistsException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getWishes(Member member) {
        return wishRepository.findByMember(member).stream()
                .map(Wish::getProduct)
                .map(Product::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addWish(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + productId));

        wishRepository.findByMemberAndProduct(member, product)
                .ifPresent(wish -> {
                    throw new WishAlreadyExistsException("이미 위시리스트에 추가된 상품입니다.");
                });

        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    @Transactional
    public void deleteWish(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + productId));

        wishRepository.deleteByMemberAndProduct(member, product);
    }
}
