package gift.item.service;

import gift.common.dto.PageResponseDto;
import gift.item.ItemEntity;
import gift.item.dto.ItemCreateDto;
import gift.item.dto.ItemResponseDto;
import gift.item.dto.ItemUpdateDto;
import gift.item.exception.ItemNotFoundException;
import gift.item.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageResponseDto<ItemResponseDto> findAll(
        int page,
        int size,
        String sortBy,
        String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
            Sort.by(sortBy).ascending() :
            Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<ItemEntity> itemEntities = itemRepository.findAll(pageable);
        Page<ItemResponseDto> pagedDtos = itemEntities.map(entity -> new ItemResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getPrice(),
            entity.getImageUrl()
        ));

        return new PageResponseDto<>(
            pagedDtos.getContent(),
            pagedDtos.getNumber() + 1,
            pagedDtos.getSize(),
            pagedDtos.getTotalElements(),
            pagedDtos.getTotalPages()
        );

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
