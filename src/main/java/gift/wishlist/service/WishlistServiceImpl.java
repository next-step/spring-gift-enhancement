package gift.wishlist.service;

import gift.entity.Member;
import gift.member.exception.MemberNotFoundException;
import gift.product.exception.ProductNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        Product product = productRepository.findById(requestDto.productId()).orElseThrow(() -> new ProductNotFoundException(requestDto.productId()));
        boolean productIsInWishlist = wishlistRepository.findByProductIdAndMemberId(requestDto.productId(), memberId).isPresent();
        if (productIsInWishlist) {
            throw new ProductIsInWishlistException(product.getName());
        }
        WishlistItem item = new WishlistItem(null, member, product, requestDto.quantity());
        WishlistItem saved = wishlistRepository.save(item);
        if (saved.getId() == null) {
            throw new OperationFailedException("저장 실패");
        }
    }

    @Override
    public void deleteWishlistItemById(Long itemId) {
        wishlistRepository.deleteById(itemId);
    }

    @Override
    public List<WishlistItemResponseDto> findAllWishlistItemsByMemberId(Long memberId) {
        List<WishlistItem> items = wishlistRepository.findAllByMemberId(memberId);
        return items.stream()
                .map(item -> new WishlistItemResponseDto(item.getId(), item.getProduct().getId(), item.getQuantity()))
                .toList();
    }

    @Override
    @Transactional
    public void updateWishlistItemById(Long itemId, Long quantity) {
        WishlistItem item = wishlistRepository.findById(itemId).orElseThrow(() -> new WishlistItemNotFoundException(itemId));
        item.updateQuantity(quantity);
    }
}
