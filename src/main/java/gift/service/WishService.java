package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponse> getAllByMemberId(Long memberId) {
        List<Wish> wishes = wishRepository.findAllWithProductByMemberId(memberId);
        return WishResponse.fromList(wishes);
    }

    @Transactional
    public Wish createOrUpdate(Long memberId, WishRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Optional<Wish> existing = wishRepository.findByMemberAndProduct(member, product);

        if (existing.isPresent()) {
            return existing.get(); // 이미 존재하는 경우 기존 위시 반환
        }
        
        Wish newWish = Wish.of(member, product);
        return wishRepository.save(newWish);
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WISHLIST_NOT_FOUND));

        if (!wish.getMember().id().equals(memberId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        
        int deletedCount = wishRepository.deleteByIdAndMemberId(id, memberId);
        if (deletedCount == 0) {
            throw new BusinessException(ErrorCode.WISHLIST_DELETE_FAILED);
        }
    }
}
