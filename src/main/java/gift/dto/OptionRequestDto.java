package gift.dto;

import gift.entity.Option;
import jakarta.validation.constraints.*;

public class OptionRequestDto {

    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&/_]*$",
            message = "이름에는 ( ) [ ] + - & / _ 외의 특수문자를 사용할 수 없습니다."
    )
    private String name;

    @NotNull
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 99999999, message = "수량은 1억 개 미만이어야 합니다.")
    private Integer quantity;

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Option   toEntity() {
        return new Option(name,quantity);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
