package gift.service;

import gift.dto.LoginMemberDto;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    private final ProductService productService;
    private final MemberService memberService;
    private final WishlistRepository wishlistRepository;

    public WishlistService(ProductService productService, MemberService memberService,
        WishlistRepository wishlistRepository) {
        this.productService = productService;
        this.memberService = memberService;
        this.wishlistRepository = wishlistRepository;
    }

    public WishResponse create(WishRequest request, LoginMemberDto memberDto) {
        Member member = memberService.findByEmail(memberDto.getEmail());
        Product product = productService.findById(request.getProductId());

        if (wishlistRepository.existsByMemberAndProduct(member, product)) {
            throw new IllegalStateException("해당 상품은 이미 위시리스트에 존재합니다.");
        }

        Wishlist wishlist = new Wishlist(member, product, request.getQuantity());
        Wishlist saved = wishlistRepository.save(wishlist);

        return new WishResponse(saved.getMember().getId(), saved.getProduct().getId(),
            saved.getQuantity());
    }

    public Page<WishResponse> findAllByMemberId(Pageable pageable, LoginMemberDto memberDto) {
        Member member = memberService.findByEmail(memberDto.getEmail());
        Page<Wishlist> wishlists = wishlistRepository.findAllByMember(pageable, member);

        return wishlists.map(w -> new WishResponse(
            w.getMember().getId(), w.getProduct().getId(), w.getQuantity()
        ));
    }

    @Transactional
    public void deleteWishlist(LoginMemberDto memberDto, Long productId) {
        Member member = memberService.findByEmail(memberDto.getEmail());
        Product product = productService.findById(productId);

        wishlistRepository.deleteByMemberAndProduct(member, product);
    }
}
