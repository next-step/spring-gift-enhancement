package gift.item.service;

import gift.item.ItemEntity;
import gift.item.dto.ItemCreateDto;
import gift.item.dto.ItemResponseDto;
import gift.item.dto.ItemUpdateDto;
import gift.item.exception.ItemNotFoundException;
import gift.item.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponseDto findItem(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));

        return new ItemResponseDto(
            itemEntity.getId(),
            itemEntity.getName(),
            itemEntity.getPrice(),
            itemEntity.getImageUrl()
        );
    }

    public List<ItemResponseDto> findAll() {
        List<ItemEntity> itemEntities = itemRepository.findAll();

        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();

        for (ItemEntity itemEntity : itemEntities) {
            ItemResponseDto dto = new ItemResponseDto(
                itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getPrice(),
                itemEntity.getImageUrl()
            );
            itemResponseDtos.add(dto);
        }

        return itemResponseDtos;
    }

    @Transactional
    public ItemResponseDto createItem(ItemCreateDto itemCreateDto) {
        ItemEntity newItemEntity = new ItemEntity(
            itemCreateDto.name(),
            itemCreateDto.price(),
            itemCreateDto.imageUrl()
        );

        ItemEntity savedItemEntity = itemRepository.save(newItemEntity);

        return new ItemResponseDto(
            savedItemEntity.getId(),
            savedItemEntity.getName(),
            savedItemEntity.getPrice(),
            savedItemEntity.getImageUrl()
        );
    }

    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemUpdateDto itemUpdateDto) {
        ItemEntity oldItemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));

        oldItemEntity.setName(itemUpdateDto.name());
        oldItemEntity.setPrice(itemUpdateDto.price());
        oldItemEntity.setImageUrl(itemUpdateDto.imageUrl());

        ItemEntity updatedItemEntity = itemRepository.save(oldItemEntity);

        return new ItemResponseDto(
            updatedItemEntity.getId(),
            updatedItemEntity.getName(),
            updatedItemEntity.getPrice(),
            updatedItemEntity.getImageUrl()
        );
    }

    @Transactional
    public void deleteItem(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));

        itemRepository.deleteById(itemEntity.getId());
    }
}
