package gift.wish.repository;

import gift.wish.domain.Wish;
import gift.wish.dto.WishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT new gift.wish.dto.WishInfo(w.id, p.id, p.name, p.price, p.imageUrl, w.quantity) " +
            "FROM Wish w JOIN w.product p WHERE w.member.id = :memberId")
    List<WishInfo> findWishesByMemberId(@Param("memberId") Long memberId);
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
