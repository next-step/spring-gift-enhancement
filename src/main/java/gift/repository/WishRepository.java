package gift.repository;

import gift.entity.Member;
import gift.entity.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMember(Member member);

    void deleteByMemberAndProductId(Member member, Long productId);
}
