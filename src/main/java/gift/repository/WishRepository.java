package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByMember(Member member);

    boolean exists(Member member, Product product);

    void delete(Member member, Product product);
}
