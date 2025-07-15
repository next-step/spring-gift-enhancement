package gift.wishlist.service;


import gift.exception.ProductNotFoundException;
import gift.exception.WishNotFoundById;
import gift.exception.WishNotFoundByMemberIdAndWishId;
import gift.member.entity.Member;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.wishlist.dto.WishRequestDto;
import gift.wishlist.dto.WishResponseDto;
import gift.wishlist.entity.Wishlist;
import gift.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public WishResponseDto addWish(Member member, WishRequestDto wishRequestDto) {
        Product product = productRepository.findById(wishRequestDto.productId())
                .orElseThrow(() -> new ProductNotFoundException(wishRequestDto.productId()));

        Wishlist wishlist = wishlistRepository.findByMemberAndProduct(member, product)
                .map(wish -> {
                    wish.addQuantity(wishRequestDto.quantity());
                    return wish;
                })
                .orElseGet(() -> {
                    return wishlistRepository.save(new Wishlist(member, product, wishRequestDto.quantity()));
                });

        return WishResponseDto.from(wishlist);
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getWishesByMember(Member member) {
        List<Wishlist> wishes = wishlistRepository.findAllByMember(member);

        return wishes.stream()
                .map(WishResponseDto::from)
                .toList();
    }

    public void deleteWish(Member member, Long wishId) {
        Wishlist wishlist = wishlistRepository.findById(wishId)
                        .orElseThrow(() -> new WishNotFoundById(wishId));

        if(!wishlist.getMember().equals(member)) {
            throw new WishNotFoundByMemberIdAndWishId(wishId, member.getId());
        }

        wishlistRepository.delete(wishlist);
    }
}
