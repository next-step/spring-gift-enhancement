package gift.wish.repository;

import gift.member.domain.Member;
import gift.product.domain.Product;
import gift.wish.domain.Wish;
import gift.wish.dto.WishListResponse;

import java.util.List;

public class WishJpaRepositoryAdapter{
    private final WishRepository repository;

    public WishJpaRepositoryAdapter(WishRepository repository){
        this.repository = repository;
    }


    public Wish save(Long memberId, Long productId) {
        return new Wish(new Member(memberId), new Product(productId), 1);
    }

    public List<WishListResponse> findWishes(Long memberId) {
        return repository.findWishesByMemberId(memberId);
    }



    public boolean isExist(Long memberId, Long productId) {
        return false;
    }
}
