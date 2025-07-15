package gift.service;

import gift.dto.WishListResponseDto;
import gift.dto.WishResponseDto;
import gift.dto.WishWithProductDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.product.ProductNotFoundException;
import gift.exception.wish.WishAlreadyExistsException;
import gift.exception.wish.WishNotFoundException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishServiceImpl(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void addWish(Member member, Long productId) {
        if (wishRepository.existsByMemberIdAndProductId(member.getId(), productId)) {
            throw new WishAlreadyExistsException("이미 위시리스트에 추가된 상품입니다.");
        }

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("상품을 찾을 수 없습니다.");
        }

        Product product = productRepository.findProductById(productId);
        Wish wish = new Wish(member, product);
        wishRepository.saveWish(wish);
    }

    @Override
    @Transactional(readOnly = true)
    public WishListResponseDto getWishList(Member member) {
        List<WishWithProductDto> wishesWithProduct = wishRepository
                .findByMemberIdWithProduct(member.getId());

        List<WishResponseDto> wishResponses = wishesWithProduct.stream()
                .map(this::mapToWishResponseDto)
                .collect(Collectors.toList());

        return new WishListResponseDto(wishResponses, wishResponses.size());
    }

    private WishResponseDto mapToWishResponseDto(WishWithProductDto wishWithProduct) {
        Product product = wishWithProduct.getProduct();
        return new WishResponseDto(
                wishWithProduct.getWishId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                wishWithProduct.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void removeWish(Member member, Long productId) {
        Wish wish = wishRepository.findByMemberIdAndProductId(member.getId(), productId)
                .orElseThrow(() -> new WishNotFoundException("위시리스트에서 해당 상품을 찾을 수 없습니다."));

        wishRepository.deleteWish(wish.getId());
    }
}