package gift.wishlist.service;

import gift.item.ItemEntity;
import gift.item.exception.ItemNotFoundException;
import gift.item.repository.ItemRepository;
import gift.member.MemberEntity;
import gift.member.exception.MemberNotFoundException;
import gift.member.repository.MemberRepository;
import gift.wishlist.WishlistEntity;
import gift.wishlist.dto.WishlistAddDto;
import gift.wishlist.dto.WishlistResponseDto;
import gift.wishlist.exception.WishlistNotFoundException;
import gift.wishlist.repository.WishlistRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishlistRepository wishlistRepository, ItemRepository itemRepository,
        MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public WishlistResponseDto add(Long memberId, WishlistAddDto wishlistAddDto) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(memberId));

        // 추가할 상품이 존재하는지 검증
        ItemEntity itemEntity = itemRepository.findById(wishlistAddDto.itemId())
            .orElseThrow(() -> new ItemNotFoundException(wishlistAddDto.itemId()));

        WishlistEntity wishlistEntity = new WishlistEntity(memberEntity, itemEntity);
        WishlistEntity savedWishlistEntity = wishlistRepository.save(wishlistEntity);

        return new WishlistResponseDto(
            savedWishlistEntity.getId(),
            memberEntity.getId(),
            itemEntity.getId(),
            itemEntity.getName(),
            itemEntity.getPrice(),
            itemEntity.getImageUrl(),
            savedWishlistEntity.getCreatedAt()
        );
    }

    public List<WishlistResponseDto> findAll(Long memberId) {
        List<WishlistEntity> wishlistEntities =
            wishlistRepository.findByMemberIdOrderByCreatedAtDesc(memberId);

        List<WishlistResponseDto> wishlistResponseDtos = new ArrayList<>();

        for (WishlistEntity wishlistEntity : wishlistEntities) {
            WishlistResponseDto dto = new WishlistResponseDto(
                wishlistEntity.getId(),
                wishlistEntity.getMember().getId(),
                wishlistEntity.getItem().getId(),
                wishlistEntity.getItem().getName(),
                wishlistEntity.getItem().getPrice(),
                wishlistEntity.getItem().getImageUrl(),
                wishlistEntity.getCreatedAt()
            );
            wishlistResponseDtos.add(dto);
        }

        return wishlistResponseDtos;

    }

    public WishlistResponseDto findWishlist(Long wishlistId, long memberId) {
        WishlistEntity wishlistEntity = wishlistRepository.findByIdAndMemberId(wishlistId, memberId)
            .orElseThrow(() -> new WishlistNotFoundException(wishlistId));

        return new WishlistResponseDto(
            wishlistEntity.getId(),
            wishlistEntity.getMember().getId(),
            wishlistEntity.getItem().getId(),
            wishlistEntity.getItem().getName(),
            wishlistEntity.getItem().getPrice(),
            wishlistEntity.getItem().getImageUrl(),
            wishlistEntity.getCreatedAt()
        );
    }

    @Transactional
    public void deleteWishlist(Long wishlistId, Long memberId) {
        WishlistEntity wishlistEntity = wishlistRepository.findByIdAndMemberId(wishlistId, memberId)
            .orElseThrow(() -> new WishlistNotFoundException(wishlistId));
        wishlistRepository.deleteById(wishlistEntity.getId());
    }
}