package giftproject.wishlist.service;

import giftproject.gift.dto.ProductResponseDto;
import giftproject.gift.service.ProductService;
import giftproject.member.entity.Member;
import giftproject.member.service.MemberService;
import giftproject.wishlist.dto.WishRequestDto;
import giftproject.wishlist.dto.WishResponseDto;
import giftproject.wishlist.entity.Wish;
import giftproject.wishlist.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishService(WishRepository wishRepository, ProductService productService,
            MemberService memberService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

    @Transactional
    public WishResponseDto save(Long memberId, WishRequestDto requestDto) {
        ProductResponseDto product = productService.findById(requestDto.productId());
        Member member = memberService.findEntityById(memberId);
        Optional<Wish> existingWishOptional = wishRepository.findByMemberIdAndProductId(memberId,
                requestDto.productId());
        Wish savedWish;

        if (existingWishOptional.isPresent()) {
            Wish existingWish = existingWishOptional.get();
            existingWish.updateQuantity(existingWish.getQuantity() + 1);
            savedWish = existingWish;
        } else {
            int distinctProductCount = wishRepository.countProductsByMemberID(memberId);
            if (distinctProductCount >= 30) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품을 최대 30종까지 담을 수 있어요.");
            }
            int initialQuantity = 1;
            Wish newWish = new Wish(member, product, initialQuantity);
            savedWish = wishRepository.save(newWish);
        }

        ProductResponseDto productResponseDto = productService.findById(
                savedWish.getProduct().getId());
        return new WishResponseDto(savedWish, productResponseDto);
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> find(Long memberId) {
        List<Wish> wishes = wishRepository.findByMemberId(memberId);

        return wishes.stream()
                .map(wish -> {
                    ProductResponseDto product = productService.findById(wish.getProduct().getId());
                    return new WishResponseDto(wish, product);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void remove(Long memberId, Long productId) {
        wishRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "위시 리스트에서 해당 상품을 찾을 수 없습니다."));

        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }
}
