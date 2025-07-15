//package gift.service.itemService;
//
//import gift.dto.itemDto.ItemCreateDto;
//import gift.dto.itemDto.ItemDto;
//import gift.dto.itemDto.ItemResponseDto;
//import gift.dto.itemDto.ItemUpdateDto;
//import gift.entity.Item;
//import gift.exception.itemException.ItemNotFoundException;
//import gift.repository.itemRepository.ItemRepository;
//import gift.repository.itemRepository.ItemRepositoryJPA;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemServiceImpl {
//
//    private final ItemRepositoryJPA itemRepository;
//
//    public ItemServiceImpl(ItemRepositoryJPA itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//
//    public ItemCreateDto saveItem(ItemCreateDto dto) {
//        Item item = new Item(dto.name(), dto.price(), dto.imageUrl());
//        Item saveditem = itemRepository.save(item);
//
//        return new ItemCreateDto(saveditem);
//    }
//
//    public List<ItemResponseDto> getItems(String name, Integer price) {
//        List<Item> items;
//        List<ItemResponseDto> result = new ArrayList<>();
//        if (name == null && price == null) {
//            items = itemRepository.getAllItems();
//        } else {
//            items = itemRepository.getItems(name, price);
//        }
//        if (items.isEmpty()) {
//            throw new ItemNotFoundException();
//        }
//
//        for (Item item : items) {
//            result.add(ItemResponseDto.from(item));
//        }
//        return result;
//    }
//
//    public void delete(String name) {
//        Item item = itemRepository.deleteItems(name);
//        if (item == null) {
//            throw new ItemNotFoundException(name);
//        }
//    }
//
//    public ItemUpdateDto updateItem(Long id, ItemUpdateDto dto) {
//        Item item = itemRepository.findById(id);
//        if (item != null) {
//            if (dto.id().equals(item.getId())) {
//                Item updatedItem = itemRepository.updateItem(id, dto.name(), dto.price(), dto.imageUrl());
//                return new ItemUpdateDto(updatedItem);
//            } else
//                throw new ItemNotFoundException();
//        } else
//            throw new ItemNotFoundException();
//    }
//
//    public ItemDto findById(Long id) {
//        List<Item> items = itemRepository.getAllItems();
//
//        for (Item item : items) {
//            if (item.getId().equals(id)) {
//                return new ItemDto(item);
//            }
//        }
//        return null;
//    }
//
//    public void deleteById(Long id) {
//        Item item = itemRepository.deleteById(id);
//        if (item == null) {
//            throw new ItemNotFoundException();
//        }
//    }
//
//    public List<ItemResponseDto> getAllItems() {
//        List<Item> items = itemRepository.getAllItems();
//        List<ItemResponseDto> result = new ArrayList<>();
//
//        for (Item item : items) {
//            result.add(ItemResponseDto.from(item));
//        }
//
//        return result;
//    }
//
//    public ItemResponseDto findItemByName(String name) {
//        Item item = itemRepository.findItemByName(name);
//
//        if (item == null) {
//            throw new ItemNotFoundException();
//        }
//
//        return ItemResponseDto.from(item);
//    }
//
//    public ItemResponseDto findItemById(Long itemId) {
//        Item item = itemRepository.findItemById(itemId);
//
//        if (item == null) {
//            throw new ItemNotFoundException();
//        }
//
//        return ItemResponseDto.from(item);
//    }
//}
