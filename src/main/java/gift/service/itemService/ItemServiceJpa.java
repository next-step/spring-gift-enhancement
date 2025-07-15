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
    public List<Item> getItems(String name, Integer price) {
        if (name == null && price == null) {
            return getAllItems();
        }
        if (name == null) {
            return itemRepository.findByPrice(price);
        }
        if (price == null) {
            itemRepository.findByName(name);
        }
        return itemRepository.findByNameAndPrice(name, price);
    }

    @Override
    public void delete(String name) {
        Item targetItem = itemRepository.findByName(name);
        itemRepository.delete(targetItem);
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
    public List<Item> getAllItems() {
        return itemRepository.findAll();
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
