package gift.service.wishlist;

import gift.dto.product.ProductResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.ResourceNotFoundException;
import gift.repository.member.MemberRepository;
import gift.repository.product.ProductRepository;
import gift.repository.wishlist.WishListRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishListServiceImpl(WishListRepository wishListRepository,
        ProductRepository productRepository,
        MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Wish create(Long productId, Long memberId) {
        Product product = productRepository.getReferenceById(productId);
        Member member = memberRepository.getReferenceById(memberId);

        Wish wish = wishListRepository.save(
            new Wish(product, member));

        return wish;
    }

    @Override
    public List<ProductResponseDto> findAll(Long memberId) {
        List<Wish> wishList = wishListRepository.findAllByMemberId(memberId);
        List<Long> idList = wishList.stream()
            .map(Wish::getProduct)
            .map(Product::getId)
            .toList();

        List<Product> productList = productRepository.findAllById(idList);
        List<ProductResponseDto> responseDtoList = productList.stream()
            .map(ProductResponseDto::from)
            .toList();

        return responseDtoList;
    }

    @Override
    public void delete(Long productId, Long memberId) {
        int deleteRow = wishListRepository.deleteByProductIdAndMemberId(productId, memberId);

        if (deleteRow <= 0) {
            throw new ResourceNotFoundException();
        }
    }
}
