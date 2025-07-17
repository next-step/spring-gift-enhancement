package gift.service;

import gift.common.code.CustomResponseCode;
import gift.common.exception.CustomException;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishServiceImpl(WishRepository wishRepository, ProductRepository productRepository,
        MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public WishResponse addWish(Long userId, WishRequest request) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));
        Product product = productRepository.findById(request.productId())
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));

        boolean exists = wishRepository.existsByMemberAndProduct(member, product);
        if (exists) {
            throw new CustomException(CustomResponseCode.ALREADY_EXISTS);
        }

        Wish savedWish = wishRepository.save(
            new Wish(null, member, product, request.quantity()));

        return WishResponse.from(savedWish);
    }

    @Override
    @Transactional
    public void deleteWish(Long userId, Long productId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));

        boolean exists = wishRepository.existsByMemberAndProduct(member, product);
        if (!exists) {
            throw new CustomException(CustomResponseCode.NOT_FOUND);
        }

        wishRepository.deleteByMemberAndProduct(member, product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishResponse> getWishes(Long userId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));

        List<Wish> wishes = wishRepository.findAllByMember(member);

        return wishes.stream()
            .map(WishResponse::from)
            .collect(Collectors.toList());
    }
}
