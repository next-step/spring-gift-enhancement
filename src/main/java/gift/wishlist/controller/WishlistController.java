package gift.wishlist.controller;

import gift.common.dto.PageResponseDto;
import gift.common.exception.InvalidSortByException;
import gift.common.exception.InvalidSortDirectionException;
import gift.common.security.AuthenticatedMember;
import gift.common.security.LoginMember;
import gift.wishlist.dto.WishlistAddDto;
import gift.wishlist.dto.WishlistResponseDto;
import gift.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    private static final Set<String> ALLOWED_SORT_FIELDS =
        Set.of("member", "item", "id", "createdAt");

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<WishlistResponseDto> addWishlist(
        @RequestBody @Valid WishlistAddDto wishlistAddDto,
        @LoginMember AuthenticatedMember member
    ) {
        WishlistResponseDto wishlistResponseDto = wishlistService.add(member.id(), wishlistAddDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistResponseDto);
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponseDto> findWishlist(
        @PathVariable Long wishlistId,
        @LoginMember AuthenticatedMember member
    ) {
        WishlistResponseDto wishlistResponseDto = wishlistService.findWishlist(
            wishlistId,
            member.id()
        );
        return ResponseEntity.status(HttpStatus.OK).body(wishlistResponseDto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<WishlistResponseDto>> findAll(
        @RequestParam(defaultValue = "1") @Positive int page,
        @RequestParam(defaultValue = "10") @Positive int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String direction,
        @LoginMember AuthenticatedMember member
    ) {
        if (!direction.equalsIgnoreCase("asc") &&
            !direction.equalsIgnoreCase("desc")) {
            throw new InvalidSortDirectionException(direction);
        }
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortByException(sortBy);
        }

        PageResponseDto<WishlistResponseDto> pagedDtos = wishlistService.findAll(
            member.id(),
            page,
            size,
            sortBy,
            direction
        );
        return ResponseEntity.ok(pagedDtos);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(
        @PathVariable Long wishlistId,
        @LoginMember AuthenticatedMember member
    ) {
        wishlistService.deleteWishlist(wishlistId, member.id());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
