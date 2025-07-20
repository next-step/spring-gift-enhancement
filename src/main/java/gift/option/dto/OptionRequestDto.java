package gift.option.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OptionRequestDto(
        @NotNull(message = "옵션의 이름을 입력해주세요.")
        @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력 가능합니다." )
        @Pattern(
                regexp = "^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]*$",
                message = "특수문자는 (), [], +, -, &, /, _ 만 가능합니다."
        )
        String name,

        @NotNull(message = "옵션의 수량을 입력해주세요.")
        @Min(value = 1, message = "수량은 1이상이어야 합니다.")
        Integer quantity
) {
}
