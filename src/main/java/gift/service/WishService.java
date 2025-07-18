package gift.service;

import gift.dto.Pagination;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import org.springframework.data.domain.Page;

public interface WishService {

    WishResponse addWish(Long memberId, WishRequest request);

    void deleteWish(Long memberId, Long productId);

    Page<WishResponse> getWishes(Long memberId, Pagination pagination);
}