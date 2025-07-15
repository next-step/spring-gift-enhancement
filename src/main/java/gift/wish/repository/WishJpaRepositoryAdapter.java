package gift.wish.repository;

import gift.member.domain.Member;
import gift.product.domain.Product;
import gift.wish.domain.Wish;
import gift.wish.dto.WishListResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class WishJpaRepositoryAdapter implements WishRepository {
    private final WishJpaRepository repository;

    public WishJpaRepositoryAdapter(WishJpaRepository repository){
        this.repository = repository;
    }

    @Override
    public Wish save(Long memberId, Long productId) {
        return new Wish(new Member(memberId), new Product(productId), 1);
    }

    @Override
    public Optional<Wish> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<WishListResponse> findWishes(Long memberId) {
        return repository.findWishesByMemberId(memberId);
    }

    @Override
    public void updateByIdAndQuantity(Long id, Integer quantity) {
        repository.updateQuantity(id, quantity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean isExist(Long memberId, Long productId) {
        return false;
    }
}
