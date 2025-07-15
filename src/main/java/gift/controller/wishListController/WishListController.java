package gift.controller.wishListController;

import gift.config.LoginUser;
import gift.dto.wishListDto.AddWishItemDto;
import gift.dto.wishListDto.ResponseWishItem;
import gift.dto.wishListDto.ResponseWishItemDto;
import gift.entity.WishItem;
import gift.service.wishListService.WishListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wish")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping
    public ResponseEntity<ResponseWishItemDto> addItem(@RequestBody @Valid AddWishItemDto dto, @LoginUser String userEmail) {

        WishItem addedWishItem = wishListService.addWishItem(dto, userEmail);

        return new ResponseEntity<>(ResponseWishItemDto.from(addedWishItem), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseWishItem> getWishItemList(@LoginUser String userEmail, @RequestParam(required = false) String name, @RequestParam(required = false) Integer price) {
        List<WishItem> wishItemList = wishListService.getItemList(name, price, userEmail);

        List<ResponseWishItemDto> wishItemDtoList = new ArrayList<>();
        for (WishItem wishItem : wishItemList) {
            wishItemDtoList.add(ResponseWishItemDto.from(wishItem));
        }

        return ResponseEntity.ok(new ResponseWishItem(wishItemDtoList));
    }
    @DeleteMapping
    public ResponseEntity<ResponseWishItemDto> deleteWishItem(@LoginUser String userEmail, @RequestParam String name) {

        WishItem targetWishItem = wishListService.deleteWishItem(name, userEmail);

        return new ResponseEntity<>(ResponseWishItemDto.from(targetWishItem), HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<ResponseWishItemDto> updateWishItem(@LoginUser String userEmail, @RequestParam Integer quantity, @RequestParam String name) {

        WishItem updatedWishItem = wishListService.updateWishItem(quantity, name, userEmail);

        return new ResponseEntity<>(ResponseWishItemDto.from(updatedWishItem), HttpStatus.ACCEPTED);
    }

}
