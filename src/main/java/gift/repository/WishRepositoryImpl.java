package gift.repository;

import gift.dto.WishWithProductDto;
import gift.entity.Wish;
import gift.exception.wish.WishNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class WishRepositoryImpl implements WishRepository {

    private final WishJpaRepository jpaRepository;

    public WishRepositoryImpl(WishJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Wish saveWish(Wish wish) {
        return jpaRepository.save(wish);
    }

    @Override
    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return jpaRepository.existsByMemberIdAndProductId(memberId, productId);
    }

    @Override
    public List<WishWithProductDto> findByMemberIdWithProduct(Long memberId) {
        List<Wish> wishes = jpaRepository.findByMemberIdWithProduct(memberId);

        return wishes.stream()
                .map(wish -> new WishWithProductDto(
                        wish.getId(),
                        wish.getMemberId(),
                        wish.getProductId(),
                        wish.getCreatedAt(),
                        wish.getProduct()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWish(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new WishNotFoundException("Wish not found with id: " + id);
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId) {
        return jpaRepository.findByMemberIdAndProductId(memberId, productId);
    }
}