package gift.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProductUpdateRequestDto(
        Long id,
        @NotBlank(message = "상품명은 필수로 입력해야합니다.")
        @Size(max = 15, message = "상품명은 15자 이내로 입력해야합니다.")
        String name,
        Long price,
        String url,
        List<ProductOptionAddRequestDto> options
) {
    public ProductUpdateRequestDto() {
        this(null, null, null, null, null);
    }
}
