package gift.service.wishListService;

import gift.dto.wishListDto.AddWishItemDto;
import gift.entity.WishItem;
import jakarta.validation.Valid;

import java.util.List;

public interface WishListService {

    WishItem addWishItem(@Valid AddWishItemDto dto, String userEmail);

    List<WishItem> getItemList(String name, Integer price, String userEmail);

    WishItem deleteWishItem(String name, String userEmail);

    WishItem updateWishItem(Integer quantity, String name, String userEmail);
}
