package gift.dto;

public class CreateWishRequest {

    Long productId;

    public CreateWishRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
