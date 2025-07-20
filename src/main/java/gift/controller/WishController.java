package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Wish;
import gift.dto.MemberResponse;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.service.WishService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getAll(@LoginMember MemberResponse member) {
        List<WishResponse> wishes = wishService.getAllByMemberId(member.id());
        return ResponseEntity.ok(wishes);
    }

    @PostMapping
    public ResponseEntity<Wish> createOrUpdate(
            @LoginMember MemberResponse member,
            @RequestBody WishRequest request
    ) {
        Wish created = wishService.createOrUpdate(member.id(), request);
        URI location = URI.create("/api/v1/wishes/" + created.getId());

        return ResponseEntity.created(location)
                .body(created);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<Void> delete(
            @LoginMember MemberResponse member,
            @PathVariable Long wishId) {
        wishService.delete(member.id(), wishId);
        return ResponseEntity.noContent().build();
    }
}
