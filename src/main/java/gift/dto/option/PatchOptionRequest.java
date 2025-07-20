package gift.dto.option;

import jakarta.validation.constraints.Min;

public record PatchOptionRequest(
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
