package gift.repository;

import gift.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findAllByMemberId(Long memberId);

    @Query("SELECT COUNT(w) > 0 FROM Wish w WHERE w.member.id = :memberId AND w.product.id = :productId")
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Wish w SET w.quantity = :quantity WHERE w.member.id = :memberId AND w.product.id = :productId")
    void updateQuantityByMemberIdAndProductId(Long memberId, Long productId, int quantity);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Wish w WHERE w.member.id = :memberId AND w.product.id = :productId")
    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}

