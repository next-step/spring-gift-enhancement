package gift.service.optionService;

import gift.dto.optionDto.OptionCreateDto;
import gift.entity.ItemOption;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptionService {
    ItemOption save(OptionCreateDto optionCreateDto, Long itemId);

    List<ItemOption> getOptions(Long itemId);

}
