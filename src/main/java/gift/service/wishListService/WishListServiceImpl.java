package gift.service.wishListService;

import gift.dto.wishListDto.CreateWishItemRequestDto;
import gift.entity.Item;
import gift.entity.User;
import gift.entity.WishItem;
import gift.exception.itemException.ItemDuplicatedException;
import gift.exception.itemException.ItemNotFoundException;
import gift.exception.userException.UserNotFoundException;
import gift.repository.wishListRepository.WishListRepository;
import gift.service.itemService.ItemService;
import gift.service.userService.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final UserService userService;
    private final ItemService itemService;

    public WishListServiceImpl(WishListRepository wishListRepository, UserService userService, ItemService itemService) {
        this.wishListRepository = wishListRepository;
        this.userService = userService;
        this.itemService = itemService;
    }


    @Override
    @Transactional
    public WishItem addWishItem(CreateWishItemRequestDto dto, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String itemName = dto.name();
        Optional<Item> findItem = itemService.findItemByName(itemName);
        if (findItem.isEmpty()) {
            throw new ItemNotFoundException(dto.name());
        }

        Item item = findItem.get();
        Integer quantity = dto.quantity();

        WishItem wishItem = new WishItem(user, item, quantity);

        if (wishListRepository.existsByItem(item)) {
            throw new ItemDuplicatedException();
        }

        return wishListRepository.save(wishItem);
    }

    @Override
    public Page<WishItem> getItemList(String name, Integer price, String userEmail, Pageable pageable) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Page<WishItem> wishItems = wishListRepository.findAllByUser(user, pageable);
        if (wishItems.isEmpty()) {
            return wishItems;
        }
        List<WishItem> filtered = new ArrayList<>();

        for (WishItem wishItem : wishItems) {
            Item item = wishItem.getItem();
            if (item.isValid(name, price)) {
                filtered.add(wishItem);
            }
        }

        return new PageImpl<>(filtered, pageable, filtered.size());
    }


    @Override
    @Transactional
    public WishItem deleteWishItem(String name, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Optional<Item> targetItem = itemService.findItemByName(name);
        if (targetItem.isEmpty()) {
            throw new ItemNotFoundException(name);
        }

        Item item = targetItem.get();

        Optional<WishItem> deletedWishItem = wishListRepository.findByUserAndItem(user, item);
        if (deletedWishItem.isEmpty()) {
            throw new ItemNotFoundException();
        }

        WishItem wishItem = deletedWishItem.get();
        wishListRepository.delete(wishItem);

        return wishItem;
    }

    @Override
    @Transactional
    public WishItem updateWishItem(Integer quantity, String name, String userEmail) {

        User user = userService.findUserByEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException();
        }

        Optional<Item> targetWishItem = itemService.findItemByName(name);
        if (targetWishItem.isEmpty()) {
            throw new ItemNotFoundException(name);
        }
        Item item = targetWishItem.get();

        Optional<WishItem> toUpdatedWishItem = wishListRepository.findByUserAndItem(user, item);
        if (toUpdatedWishItem.isEmpty()) {
            throw new ItemNotFoundException();
        }

        WishItem wishItem = toUpdatedWishItem.get();
        WishItem updatedWishItem = wishItem.changeQuantity(quantity);

        return wishListRepository.save(updatedWishItem);
    }

}
