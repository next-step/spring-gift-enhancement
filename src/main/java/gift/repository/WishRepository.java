package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.entity.WishWithProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndProduct(Member member, Product product);

    Page<Wish> findByMember(Member member, Pageable pageable);

    @Query("SELECT w FROM Wish w WHERE w.member.id = :memberId AND w.product.id = :productId")
    Optional<Wish> findByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Wish w SET w.quantity = :quantity WHERE w.member.id = :memberId AND w.product.id = :productId")
    void updateQuantity(@Param("memberId") Long memberId, @Param("productId") Long productId, @Param("quantity") int quantity);

    @Modifying
    @Query("DELETE FROM Wish w WHERE w.member.id = :memberId AND w.product.id = :productId")
    void deleteByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);

    @Query("SELECT new gift.entity.WishWithProduct(w.member.id, w.product, w.quantity) FROM Wish w WHERE w.member.id = :memberId")
    List<WishWithProduct> findByMemberIdWithPagination(@Param("memberId") Long memberId, Pageable pageable);
}