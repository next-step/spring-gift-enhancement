package gift.wish.service;

import gift.member.entity.Member;
import gift.member.repository.MemberRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.wish.dto.WishRequestDto;
import gift.wish.dto.WishResponseDto;
import gift.wish.entity.Wish;
import gift.wish.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gift.wish.dto.WishResponseDto.fromEntity;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository,
                       MemberRepository memberRepository,
                       ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponseDto> getWishlist(WishRequestDto dto) {

        return wishRepository.findByMemberId(dto.getMemberId())
                .stream().map(WishResponseDto::fromEntity).toList();
    }

    public WishResponseDto addWish(WishRequestDto dto) {

        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("member를 찾을 수 없습니다"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException("product를 찾을 수 없습니다"));

        Wish wish = new Wish(null, member, product, dto.getQuantity());

        List<Wish> list = wishRepository.findByMemberId(wish.getMember().getId());
        List<Long> productIds = list.stream().map(Wish::getProduct).map(Product::getId).toList();
        if (productIds.stream().anyMatch(dto.getProductId()::equals)) {
            throw new IllegalArgumentException("이미 추가 되어있습니다!");
        }


        return fromEntity(wishRepository.save(wish));
    }

    @Transactional
    public void deleteWish(WishRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("member를 찾을 수 없습니다"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException("product를 찾을 수 없습니다"));
        Wish wish = new Wish(null, member, product, null);
        wishRepository.deleteByMemberIdAndProductId(wish.getMember().getId(), wish.getProduct().getId());
    }

}