package gift.wishlist.service;

import gift.wishlist.dto.WishlistItemRequestDto;
import gift.wishlist.dto.WishlistItemResponseDto;
import gift.entity.Product;
import gift.entity.WishlistItem;
import gift.exception.OperationFailedException;
import gift.product.exception.ProductIsInWishlistException;
import gift.wishlist.exception.WishlistItemNotFoundException;
import gift.member.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addWishlistItem(Long memberId, WishlistItemRequestDto requestDto) {
        memberRepository.findMemberByIdOrElseThrow(memberId);
        Product product = productRepository.findProductByIdOrElseThrow(requestDto.productId());
        boolean productIsInWishlist = wishlistRepository.findProductInMemberById(memberId, requestDto.productId()).isPresent();
        if (productIsInWishlist) {
            throw new ProductIsInWishlistException(product.name());
        }
        WishlistItem item = new WishlistItem(null, memberId, requestDto.productId(), requestDto.quantity());
        int result = wishlistRepository.addWishlistItem(item);
        if (result == 0) {
            throw new OperationFailedException();
        }
    }

    @Override
    public void deleteWishlistItemById(Long itemId) {
        int result = wishlistRepository.deleteById(itemId);
        if (result == 0) {
            throw new OperationFailedException();
        }
    }

    @Override
    public List<WishlistItemResponseDto> findAllWishlistItemsByMemberId(Long memberId) {
        List<WishlistItem> items = wishlistRepository.findAllWishlistItemsByMemberId(memberId);
        return items.stream()
                .map(item -> new WishlistItemResponseDto(item.id(), item.productId(), item.quantity()))
                .toList();
    }

    @Override
    public void updateWishlistItemById(Long itemId, Long quantity) {
        int result = wishlistRepository.updateWishlistItemById(itemId, quantity);
        if (result == 0) {
            throw new OperationFailedException();
        }
    }

    public WishlistItem findWishlistItemByIdOrElseThrow(Long itemId) {
        return wishlistRepository.findWishlistById(itemId).orElseThrow(() -> new WishlistItemNotFoundException(itemId));
    }
}
