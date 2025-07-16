package gift.controller;

import gift.auth.Login;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Member;
import gift.service.WishService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<WishResponseDto>> getWishes(@Login Member member, @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(wishService.getWishes(member.getId(), pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addWish(
            @Login Member member,
            @Valid @RequestBody WishRequestDto wishRequestDto
    ) {
        wishService.addWish(member.getId(), wishRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWish(
            @Login Member member,
            @PathVariable Long productId
    ) {
        wishService.deleteWish(member.getId(), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
