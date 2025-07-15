package gift.service;

import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.DuplicateWishException;
import gift.exception.UpdateFailedException;
import gift.repository.H2ProductRepository;
import gift.repository.H2WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WishService {

    private final H2WishRepository wishRepository;
    private final H2ProductRepository productRepository;

    public WishService(H2WishRepository wishRepository, H2ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponseDto> getAllWishes(Long userId) {
        return wishRepository.findAllByUserId(userId)
                .stream()
                .map(row -> new WishResponseDto(row.productId(), row.productName(), row.quantity()))
                .toList();
    }

    public WishResponseDto createWish(Long userId, WishRequestDto wishRequestDto) {
        Wish wish = wishRequestDto.toEntity();
        wish.setUserId(userId);

        // 이미 wish가 있을 경우
        if (wishRepository.isWishExist(wish.getUserId(), wish.getProductId())) {
            throw new DuplicateWishException("이미 동일한 상품이 존재합니다.");
        }

        Product product = productRepository.findById(wish.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다. " +
                                                              "productId = " + wish.getProductId()));

        wishRepository.save(wish);

        return new WishResponseDto(product.getId(), product.getName(), wish.getQuantity());
    }

    public WishResponseDto updateWish(Long userId, WishRequestDto wishRequestDto) {
        Wish wish = wishRequestDto.toEntity();
        wish.setUserId(userId);

        if (!wishRepository.isWishExist(userId, wish.getProductId())) {
            throw new UpdateFailedException("수정 요청한 위시가 존재하지 않습니다.");
        }

        Product product = productRepository.findById(wish.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다. " +
                        "productId = " + wish.getProductId()));

        wishRepository.update(wish);

        return new WishResponseDto(product.getId(), product.getName(), wish.getQuantity());
    }

    public void deleteWish(Long userId, Long productId) {
        if (!wishRepository.isWishExist(userId, productId)) {
            throw new NoSuchElementException("삭제 요청한 위시가 존재하지 않습니다.");
        }

        wishRepository.deleteById(userId, productId);
    }
}
