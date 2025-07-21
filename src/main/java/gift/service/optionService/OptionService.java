package gift.service.optionService;

import gift.dto.optionDto.OptionCreateDto;
import gift.entity.ItemOption;

public interface OptionService {
    ItemOption save(OptionCreateDto optionCreateDto, Long itemId);
}
