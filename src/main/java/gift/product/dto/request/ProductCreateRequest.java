package gift.product.dto.request;

import gift.product.entity.Product;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
        Long giftId,
        @Size(min = 1, max = 15, message = "상품 명은 공백포함 15자 이하여야 합니다.")
        String giftName,
        Integer giftPrice,
        String giftPhotoUrl
) {
    public Product toEntity () {
        return new Product(
                this.giftId(),
                this.giftName(),
                this.giftPrice(),
                this.giftPhotoUrl()
        );
    }
}
