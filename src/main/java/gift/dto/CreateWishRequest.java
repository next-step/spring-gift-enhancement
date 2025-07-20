package gift.dto;

public class CreateWishRequest {

    private final Long productId;

    public CreateWishRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
