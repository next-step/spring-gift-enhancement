package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface WishJpaRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT COUNT(w) > 0 FROM Wish w WHERE w.member.id = :memberId AND w.product.id = :productId")
    boolean existsByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);

    @Query("SELECT w FROM Wish w JOIN FETCH w.product WHERE w.member.id = :memberId ORDER BY w.createdAt DESC")
    List<Wish> findByMemberIdWithProduct(@Param("memberId") Long memberId);

    @Query("SELECT w FROM Wish w WHERE w.member.id = :memberId AND w.product.id = :productId")
    Optional<Wish> findByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);
}