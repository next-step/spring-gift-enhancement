package gift.service.optionService;

import gift.dto.optionDto.OptionCreateDto;
import gift.entity.Item;
import gift.entity.ItemOption;
import gift.exception.itemException.ItemNotFoundException;
import gift.repository.itemRepository.ItemRepository;
import gift.repository.optionRepository.OptionRepository;
import gift.service.itemService.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;
    private final ItemService itemService;

    public OptionServiceImpl(OptionRepository optionRepository, ItemRepository itemRepository, ItemService itemService) {
        this.optionRepository = optionRepository;
        this.itemService = itemService;
    }

    @Override
    public ItemOption save(OptionCreateDto optionCreateDto, Long itemId) {
        Item item = itemService.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);

        ItemOption itemOption = new ItemOption(item,optionCreateDto.OptionName(), optionCreateDto.quantity());

        return optionRepository.save(itemOption);
    }

    @Override
    public List<ItemOption> getOptions(Long itemId) {
        Item item = itemService.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);

        return item.getOptions();
    }


}
