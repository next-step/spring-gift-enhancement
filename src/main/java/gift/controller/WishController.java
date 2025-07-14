package gift.controller;

import gift.annotation.UserValid;
import gift.dto.UserInfoDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping()
    public ResponseEntity<List<WishResponseDto>> findUserWishes(@UserValid UserInfoDto userInfoDto) {
        return ResponseEntity.ok(wishService.findUserWishes(userInfoDto));
    }

    @PostMapping()
    public ResponseEntity<WishResponseDto> addWish(@UserValid UserInfoDto userInfoDto, @RequestBody WishRequestDto wishRequestDto) {
        return new ResponseEntity<>(wishService.addWish(userInfoDto, wishRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<Void> updateWish(@UserValid UserInfoDto userInfoDto, @RequestBody WishRequestDto wishrequestDto) {
        wishService.updateWish(userInfoDto, wishrequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteWish(@UserValid UserInfoDto userInfoDto, @RequestBody WishRequestDto wishRequestDto) {
        wishService.deleteWish(userInfoDto, wishRequestDto);
        return ResponseEntity.noContent().build();
    }
}
