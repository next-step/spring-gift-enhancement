package gift.repository;

import gift.entity.Item;
import gift.entity.Member;
import gift.entity.Wish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query(value = "SELECT w FROM Wish w JOIN FETCH w.product p WHERE w.member = :member",
        countQuery = "SELECT count(w) FROM Wish w WHERE w.member = :member")
    Page<Wish> findAllByMemberWithProduct(@Param("member") Member member, Pageable pageable);

    Optional<Wish> findByMemberAndProduct(Member member, Item product);
}