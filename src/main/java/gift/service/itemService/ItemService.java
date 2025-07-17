package gift.service.itemService;

import gift.dto.itemDto.ItemCreateDto;
import gift.dto.itemDto.ItemUpdateDto;
import gift.entity.Item;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item saveItem(ItemCreateDto dto);

    List<Item> getItems(String name, Integer price);

    void delete(String name);

    Item updateItem(Long id, ItemUpdateDto dto);

    Optional<Item> findById(Long id);

    void deleteById(Long id);

    List<Item> getAllItems();

    Optional<Item> findItemByName(@NotNull String name);

    Optional<Item> findItemById(Long itemId);
}
