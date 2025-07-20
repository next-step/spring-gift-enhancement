package gift.dto.option;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record PatchOptionRequest(
    @NotNull(message = "옵션 ID는 필수입니다.")
    Long id,
    @Min(value = 1, message = "증감 시킬 수량은 1 이상이어야 합니다.")
    Long quantity,
    Boolean increment
) {
    public PatchOptionRequest {
        // 기본값 설정
        if (quantity == null) {
            quantity = 1L;
        }
        if (increment == null) {
            increment = true;
        }

    }
}
