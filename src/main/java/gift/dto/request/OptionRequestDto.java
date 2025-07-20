package gift.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OptionRequestDto(

    @NotBlank
    String optionName,

    @NotBlank
    int quantity

) {

}