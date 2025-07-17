package gift.wishlist.service;

import gift.wishlist.dto.WishlistItemRequestDto;
import gift.wishlist.dto.WishlistItemResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {
    void addWishlistItem(Long memberId, WishlistItemRequestDto requestDto);

    void deleteWishlistItemById(Long itemId);

    List<WishlistItemResponseDto> findAllWishlistItemsByMemberId(Long memberId);
    void updateWishlistItemById(Long itemId, Long quantity);
}
