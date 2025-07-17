package gift.repository;

import gift.entity.Member;
import gift.entity.Wish;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    // 특정 회원의 모든 위시리스트 (최신순 정렬)
    List<Wish> findByMemberOrderByIdDesc(Member member, Pageable pageable);

    // 특정 회원이 특정 상품을 찜했는지 여부
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}