package gift.item.service;

import gift.item.ItemEntity;
import gift.item.OptionEntity;
import gift.item.dto.OptionCreateDto;
import gift.item.dto.OptionResponseDto;
import gift.item.dto.OptionUpdateDto;
import gift.item.exception.ItemNotFoundException;
import gift.item.exception.OptionNotFoundException;
import gift.item.repository.ItemRepository;
import gift.item.repository.OptionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ItemRepository itemRepository;

    public OptionService(OptionRepository optionRepository, ItemRepository itemRepository) {
        this.optionRepository = optionRepository;
        this.itemRepository = itemRepository;
    }

    public List<OptionResponseDto> findOptionsByItemId(Long itemId) {
        itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));

        List<OptionEntity> optionEntities = optionRepository.findByItemId(itemId);

        return optionEntities.stream().map(optionEntity ->
            new OptionResponseDto(
                optionEntity.getId(),
                optionEntity.getName(),
                optionEntity.getQuantity()
            )
        ).toList();
    }

    @Transactional
    public OptionResponseDto createOption(Long itemId, OptionCreateDto optionCreateDto) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));

        OptionEntity newOptionEntity = new OptionEntity(
            optionCreateDto.name(),
            optionCreateDto.quantity(),
            itemEntity
        );

        OptionEntity savedOptionEntity = optionRepository.save(newOptionEntity);

        return new OptionResponseDto(
            savedOptionEntity.getId(),
            savedOptionEntity.getName(),
            savedOptionEntity.getQuantity()
        );
    }

    @Transactional
    public OptionResponseDto updateOption(Long optionId, OptionUpdateDto optionUpdateDto) {
        OptionEntity oldOptionEntity = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(optionId));

        oldOptionEntity.setName(optionUpdateDto.name());
        oldOptionEntity.setQuantity(optionUpdateDto.quantity());

        OptionEntity updatedOptionEntity = optionRepository.save(oldOptionEntity);
        return new OptionResponseDto(
            updatedOptionEntity.getId(),
            updatedOptionEntity.getName(),
            updatedOptionEntity.getQuantity()
        );
    }

    @Transactional
    public void deleteOption(Long optionId) {
        OptionEntity oldOptionEntity = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(optionId));

        optionRepository.delete(oldOptionEntity);
    }
    
}
