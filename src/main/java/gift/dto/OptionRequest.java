package gift.dto;

import gift.validation.ValidProductName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OptionRequest {

    @NotNull(message = "상품 ID는 필수 입력 값입니다.")
    private Long productId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 50, message = "이름은 최대 50자까지 입력 가능합니다.")
    @ValidProductName   // 특수문자 유효성 검사
    private String name;

    @NotNull(message = "수량은 필수 입력 값입니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    @Max(value = 100000000, message = "수량은 1억 미만이어야 합니다.")
    private Integer quantity;

    public OptionRequest() {
    }

    public OptionRequest(Long productId, String name, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
