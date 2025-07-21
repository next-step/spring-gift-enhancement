package gift.dto.optionDto;

import gift.entity.ItemOption;

public record OptionCreateDto(String OptionName, Integer quantity) {
    public OptionCreateDto(ItemOption itemOption) {

    }
}
