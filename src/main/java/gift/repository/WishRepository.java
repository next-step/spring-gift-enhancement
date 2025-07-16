package gift.repository;

import gift.entity.Item;
import gift.entity.Member;
import gift.entity.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w JOIN FETCH w.product WHERE w.member = :member")
    List<Wish> findAllByMemberWithProduct(@Param("member") Member member);

    Optional<Wish> findByMemberAndProduct(Member member, Item product);
}