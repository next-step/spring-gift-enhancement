package gift.repository;

import gift.entity.Item;
import gift.entity.Member;
import gift.entity.Wish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @EntityGraph(attributePaths = {"product"})
    Page<Wish> findAllByMember(@Param("member") Member member, Pageable pageable);

    Optional<Wish> findByMemberAndProduct(Member member, Item product);
}