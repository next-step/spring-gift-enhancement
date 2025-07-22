package gift.option.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record OptionUpdateRequest(@Range(min = 1, max = 99999999) int quantity) {
}
