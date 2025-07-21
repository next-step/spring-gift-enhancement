package gift.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateOptionRequest {

    @NotNull
    @Size(max = 50, message = "공백 포함 최대 50자까지만 입력 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = "특수문자는 (), [], +, -, &, /, _ 만 가능합니다.")
    private final String name;

    @NotNull
    private final int quantity;

    private final Long productId;


    public CreateOptionRequest(String name, int quantity, Long productId) {
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }
}
