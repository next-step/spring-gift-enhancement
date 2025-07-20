package gift.dto.option;

import gift.common.validation.annotation.ValidCharSet;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateOptionRequest(
        @ValidCharSet(message = "옵션 이름은 문자, 숫자, 공백, 특수문자( ( ), [ ], +, -, &, /, _)만 허용됩니다.")
        @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력 가능합니다.")
        String name,
        @Min(value = 0, message = "수량은 0 이상이어야 합니다.")
        Long quantity
) {
}
