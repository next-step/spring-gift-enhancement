package gift.service.wishListService;

import gift.dto.wishListDto.AddWishItemDto;
import gift.entity.Item;
import gift.entity.User;
import gift.entity.WishItem;
import gift.exception.itemException.ItemDuplicatedException;
import gift.exception.itemException.ItemNotFoundException;
import gift.exception.itemException.UserInputException;
import gift.exception.userException.UserNotFoundException;
import gift.repository.wishListRepository.WishListRepositoryJPA;
import gift.service.itemService.ItemService;
import gift.service.userService.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImpl implements WishListService{

    private final WishListRepositoryJPA wishListRepository;
    private final UserService userService;
    private final ItemService itemService;

    public WishListServiceImpl(WishListRepositoryJPA wishListRepository, UserService userService, ItemService itemService) {
        this.wishListRepository = wishListRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public WishItem addWishItem(AddWishItemDto dto, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Optional<Item> findItem = itemService.findItemByName(dto.name());
        if (findItem.isEmpty()) {
            throw new ItemNotFoundException(dto.name());
        }

        Item item = findItem.get();
        Integer quantity = dto.quantity();

        WishItem wishItem = new WishItem(user, item, quantity);

        if (wishListRepository.existsByItem(item)) {
            throw new ItemDuplicatedException();
        }

        WishItem savedWishItem = wishListRepository.save(wishItem);

        return savedWishItem;
    }

    @Override
    public List<WishItem> getItemList(String name, Integer price, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        List<WishItem> wishItems = wishListRepository.findAllByUser(user);
        if (wishItems.isEmpty()) {
            throw new ItemNotFoundException();
        }

        List<WishItem> result = new ArrayList<>();
        for (WishItem wishItem : wishItems) {
            Item item = wishItem.getItem();
            if (isValid(item, name, price)) {
                result.add(wishItem);
            }
        }

        return result;
    }

    private boolean isValid(Item item, String name, Integer price) {
        boolean nameMatches = (name == null || item.getName().equals(name));
        boolean priceMatches = (price == null || item.getPrice().equals(price));

        return nameMatches && priceMatches;
    }

    private List<WishItem> getWishItems(List<WishItem> wishItems, String name, Integer price) {
        List<WishItem> result = new ArrayList<>();

        for (WishItem wishItem : wishItems) {
            Item item = wishItem.getItem();
            if (item == null) {
                if (name == null && price == null) {
                    throw new UserInputException();
                }
                continue;
            }

            if ((name == null && price == null) || isValid(item, name, price)) {
                result.add(wishItem);
            }
        }

        return result;
    }

    @Override
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
        wishItem.setQuantity(quantity);

        WishItem upatedWishItem = wishListRepository.save(wishItem);

        return upatedWishItem;
    }

}
