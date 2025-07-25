package gift.repository;

import gift.entity.Wish;
import gift.entity.WishId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, WishId> {
    // N+1 방지 + 최적화된 페이지네이션
    @Query(value = "SELECT w FROM Wish w JOIN FETCH w.product WHERE w.member.id = :memberId",
            countQuery = "SELECT COUNT(w) FROM Wish w WHERE w.member.id = :memberId")
    Page<Wish> findByMemberIdOptimized(@Param("memberId") String memberId, Pageable pageable);
}


