package gift.wish.repository;

import gift.wish.domain.Wish;
import gift.wish.dto.WishListResponse;

import java.util.List;
import java.util.Optional;

public interface WishRepository {

    Wish save(Long memberId, Long productId);

    Optional<Wish> findById(Long id);

    List<WishListResponse> findWishes(Long memberId);

    void updateByIdAndQuantity(Long id, Integer quantity);

    void deleteById(Long id);

    boolean isExist(Long memberId, Long productId);
}