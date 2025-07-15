package gift.service;

import gift.dto.ProductResponse;
import gift.dto.WishRequest;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository,
            MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void addWish(Long memberId, WishRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원이 존재하지 않습니다: " + memberId));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NoSuchElementException(
                        "해당 ID의 상품이 존재하지 않습니다: " + request.productId()));

        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getWishes(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원이 존재하지 않습니다: " + memberId));

        List<Wish> wishes = wishRepository.findByMember(member);
        return wishes.stream()
                .map(wish -> new ProductResponse(wish.getProduct()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteWish(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원이 존재하지 않습니다: " + memberId));

        wishRepository.deleteByMemberAndProductId(member, productId);
    }
}
