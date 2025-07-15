package gift.service;

import gift.dto.UserInfoDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Wish;
import gift.exception.DuplicateException;
import gift.exception.NotFoundException;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishResponseDto> findUserWishes(UserInfoDto userInfoDto) {
        return wishRepository.findByUserId(userInfoDto.id()).stream().map(WishResponseDto::new).collect(Collectors.toList());
    }

    public WishResponseDto addWish(UserInfoDto userInfoDto, WishRequestDto wishRequestDto) {
        if (wishRepository.existsByProductId(wishRequestDto.productId())) {  // 제품 중복 검사
            throw new DuplicateException("이미 리스트에 존재하는 제품입니다.");
        }

        Wish wish = new Wish(wishRequestDto.id(), userInfoDto.id(), wishRequestDto.productId(), wishRequestDto.quantity());
        return new WishResponseDto(wishRepository.save(wish));
    }

    public void updateWish(WishRequestDto wishRequestDto) {
        Wish wish = wishRepository.findById(wishRequestDto.id()).orElseThrow(() -> new NotFoundException("wish", wishRequestDto.id()));
        wish.update(wishRequestDto.quantity());
    }

    public void deleteWish(WishRequestDto wishRequestDto) {
        wishRepository.deleteById(wishRequestDto.id());
    }
}