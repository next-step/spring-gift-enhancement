package gift.service;

import gift.dto.UserInfoDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Wish;
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
        return wishRepository.findUserWishes(userInfoDto.id()).stream().map(WishResponseDto::new).collect(Collectors.toList());
    }

    public WishResponseDto addWish(UserInfoDto userInfoDto, WishRequestDto wishRequestDto) {
        wishRepository.checkProductDuplicate(userInfoDto.id(), new Wish(wishRequestDto)); // 제품 중복 검사
        return new WishResponseDto(wishRepository.addWish(userInfoDto.id(), new Wish(wishRequestDto)));
    }

    public void updateWish(UserInfoDto userInfoDto, WishRequestDto wishRequestDto) {
        wishRepository.updateWish(userInfoDto.id(), new Wish(wishRequestDto));
    }

    public void deleteWish(UserInfoDto userInfoDto, WishRequestDto wishRequestDto) {
        wishRepository.deleteWish(userInfoDto.id(), new Wish(wishRequestDto));
    }
}