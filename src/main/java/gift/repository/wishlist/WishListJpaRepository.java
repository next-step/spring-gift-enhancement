package gift.repository.wishlist;

import gift.domain.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListJpaRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByMemberId(Long memberId);

    Optional<WishList> findByMemberIdAndProductId(Long memberId, Long productId);

    Page<WishList> findAllPageByMemberId(Long memberId, Pageable pageable);
}
