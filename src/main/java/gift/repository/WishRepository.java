package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    boolean existsByMember_IdAndProduct_Id(Long memberId, Long productId);
    Optional<Wish> findByMember_IdAndProduct_Id(Long memberId, Long productId);
    List<Wish> findAllByMember_Id(Long memberId);
}