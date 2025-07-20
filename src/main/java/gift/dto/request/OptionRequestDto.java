package gift.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OptionRequestDto(

    @NotBlank
    String optionName,

    @Min(1)
    @Max(1000000000 - 1)
    int quantity

) {

}