package gift.repository.wishlist;

import gift.entity.Wish;
import jakarta.persistence.NamedAttributeNode;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WishListRepository extends JpaRepository<Wish, Long> {
    Wish findByProductIdAndMemberId(Long productId, Long memberId);

    @EntityGraph(attributePaths = {"product", "member"})
    List<Wish> findAllByMemberId(Long memberId);

    @Transactional
    int deleteByProductIdAndMemberId(Long productId, Long memberId);
}