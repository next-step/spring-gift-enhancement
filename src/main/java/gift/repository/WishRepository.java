package gift.repository;

import gift.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMemberEmail(String email);

    boolean existsByMemberEmailAndProductId(String email, Long productId);
}
