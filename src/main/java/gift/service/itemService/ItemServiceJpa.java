package gift.service.itemService;

import gift.dto.itemDto.ItemCreateDto;
import gift.dto.itemDto.ItemDto;
import gift.dto.itemDto.ItemResponseDto;
import gift.dto.itemDto.ItemUpdateDto;
import gift.entity.Item;

import gift.repository.itemRepository.ItemRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceJpa implements ItemService {
    private final ItemRepositoryJPA itemRepository;

    public ItemServiceJpa(ItemRepositoryJPA itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item saveItem(ItemCreateDto dto) {
        Item item = new Item(dto.name(), dto.price(),dto.imageUrl());

        return itemRepository.save(item);
    }

    @Override
    public List<ItemResponseDto> getItems(String name, Integer price) {
        return List.of();
    }

    @Override
    public void delete(String name) {

    }

    @Override
    public ItemUpdateDto updateItem(Long id, ItemUpdateDto dto) {
        return null;
    }

    @Override
    public ItemDto findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ItemResponseDto> getAllItems() {
        return List.of();
    }

    @Override
    public ItemResponseDto findItemByName(String name) {
        return null;
    }

    @Override
    public ItemResponseDto findItemById(Long itemId) {
        return null;
    }
}
