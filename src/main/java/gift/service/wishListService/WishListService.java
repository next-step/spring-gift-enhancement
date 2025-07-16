package gift.service.wishListService;

import gift.dto.wishListDto.CreateWishItemRequestDto;
import gift.entity.WishItem;
import jakarta.validation.Valid;

import java.util.List;

public interface WishListService {

    WishItem addWishItem(@Valid CreateWishItemRequestDto dto, String userEmail);

    List<WishItem> getItemList(String name, Integer price, String userEmail);

    WishItem deleteWishItem(String name, String userEmail);

    WishItem updateWishItem(Integer quantity, String name, String userEmail);
}
