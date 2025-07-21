package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WishRepositoryImpl implements WishRepository {

    private final WishJpaRepository wishJpaRepository;

    public WishRepositoryImpl(WishJpaRepository wishJpaRepository) {
        this.wishJpaRepository = wishJpaRepository;
    }

    @Override
    public Wish save(Wish wish) {
        Objects.requireNonNull(wish, "wish는 null일 수 없습니다.");
        return wishJpaRepository.save(wish);
    }

    @Override
    public void update(Long id, Wish updatedWish) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");
        Objects.requireNonNull(updatedWish, "updatedWish는 null일 수 없습니다.");

        Wish existingWish = wishJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 위시가 존재하지 않아 업데이트할 수 없습니다: " + id));

        // JPA에서는 엔티티를 직접 교체할 수 없으므로, 필요시 필드를 개별적으로 업데이트해야 합니다.
        wishJpaRepository.save(updatedWish);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        wishJpaRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public int deleteByIdAndMemberId(Long id, Long memberId) {
        return wishJpaRepository.deleteByIdAndMemberId(id, memberId);
    }

    @Override
    public List<Wish> findAll() {
        return wishJpaRepository.findAll();
    }

    @Override
    public Optional<Wish> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return wishJpaRepository.findById(id);
    }

    @Override
    public List<Wish> findAllWithProductByMemberId(Long memberId) {
        return wishJpaRepository.findByMemberIdWithProduct(memberId);
    }

    @Override
    public Optional<Wish> findByMemberAndProduct(Member member, Product product) {
        return wishJpaRepository.findByMemberAndProduct(member, product);
    }
}
