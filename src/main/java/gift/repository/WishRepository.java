package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    public List<Wish> findByMember(Member member);

    boolean existsByMemberAndProduct(Member member, Product product);

    void deleteByMemberAndProduct(Member member, Product product);
}
