package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import java.util.List;
import java.util.Optional;

public interface WishRepository {

    Wish save(Wish wish);

    void update(Long id, Wish updatedWish);

    void deleteAllByIds(List<Long> ids);

    int deleteByIdAndMemberId(Long id, Long memberId);

    List<Wish> findAll();

    Optional<Wish> findById(Long id);

    List<Wish> findAllWithProductByMemberId(Long memberId);

    Optional<Wish> findByMemberAndProduct(Member member, Product product);
}
