package gift.repository.wishlist;

import gift.entity.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WishListRepository extends JpaRepository<Wish, Long> {
    Wish findByProductIdAndMemberId(Long productId, Long memberId);

    @Query("select w from Wish w join fetch w.product join fetch w.member where w.member.id = :memberId")
    List<Wish> findAllByMemberId(@Param("memberId") Long memberId);

    @Transactional
    int deleteByProductIdAndMemberId(Long productId, Long memberId);
}