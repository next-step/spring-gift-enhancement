package gift.dto;

import gift.entity.Product;
import gift.entity.vo.Money;
import gift.entity.vo.ProductName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank(message = "상품 이름은 비워둘 수 없습니다.")
        String name,

        @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
        Integer price,

        @NotBlank(message = "이미지 URL은 비워둘 수 없습니다.")
        String imageUrl
) {
    public Product toEntity() {
        return new Product(new ProductName(name), new Money(price), imageUrl);
    }
}