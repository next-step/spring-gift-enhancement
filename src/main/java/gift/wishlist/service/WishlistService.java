package gift.wishlist.service;

import gift.wishlist.dto.WishlistItemRequestDto;
import gift.wishlist.dto.WishlistItemResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {
    public void addWishlistItem(Long memberId, WishlistItemRequestDto requestDto);

    public void deleteWishlistItemById(Long itemId);

    public List<WishlistItemResponseDto> findAllWishlistItemsByMemberId(Long memberId);

    public void updateWishlistItemById(Long itemId, Long quantity);
}
