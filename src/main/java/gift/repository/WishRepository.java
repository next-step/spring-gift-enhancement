package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByUserId(Long userId);

    Optional<Wish> findByUserIdAndProductId(Long userId, Long productId);

    Boolean existsByUserIdAndProductId(Long userId, Long productId);
}
