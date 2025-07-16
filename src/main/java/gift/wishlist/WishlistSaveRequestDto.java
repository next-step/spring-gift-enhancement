package gift.wishlist;

public class WishlistSaveRequestDto {

    private Long productId;

    public WishlistSaveRequestDto() {}

    public WishlistSaveRequestDto(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
