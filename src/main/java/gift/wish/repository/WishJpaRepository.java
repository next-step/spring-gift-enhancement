package gift.wish.repository;

import gift.wish.domain.Wish;
import gift.wish.dto.WishListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface WishJpaRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT new gift.wish.dto.WishListResponse(w.id, p.id, p.name, p.price, p.imageUrl, w.quantity) " +
            "FROM Wish w JOIN w.product p WHERE w.member.id = :memberId")
    List<WishListResponse> findWishesByMemberId(@Param("memberId") Long memberId);
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
    @Modifying
    @Query("UPDATE Wish w SET w.quantity = :quantity WHERE w.id = :id")
    void updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
}
