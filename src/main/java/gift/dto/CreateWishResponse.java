package gift.dto;

public class CreateWishResponse {

    private final Long wishId;
    private final Long productId;

    public CreateWishResponse(Long wishId, Long productId) {
        this.wishId = wishId;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}