package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Collection<Wish> findByUserId(Long userId);
    boolean existsByProductId(Long productId);
}