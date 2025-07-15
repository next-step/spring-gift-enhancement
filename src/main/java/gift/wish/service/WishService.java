package gift.wish.service;

import gift.wish.dto.WishRequestDto;
import gift.wish.dto.WishResponseDto;
import gift.wish.entity.Wish;
import gift.wish.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishResponseDto> getWishlist(WishRequestDto dto) {

        Wish wish = new Wish(null, dto.getMemberId(),null,null);

        return wishRepository.getWishList(wish).stream()
                .map(WishResponseDto::fromEntity).toList();
    }

    public WishResponseDto addWish(WishRequestDto dto) {

        Wish wish = new Wish(null, dto.getMemberId(), dto.getProductId(), dto.getQuantity());

        List<Wish> list = wishRepository.getWishList(wish);
        List<Long> productIds = list.stream().map(Wish::getProductId).toList();
        if(productIds.stream().anyMatch(dto.getProductId()::equals)){
            throw new IllegalArgumentException("이미 추가 되어있습니다!");
        }


        return WishResponseDto.fromEntity(wishRepository.addWish(wish));
    }

    public void deleteWish(WishRequestDto dto) {
        Wish wish = new Wish(null, dto.getMemberId(), dto.getProductId(), null);
        wishRepository.deleteWish(wish);
    }

}
