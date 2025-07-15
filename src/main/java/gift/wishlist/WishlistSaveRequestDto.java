package gift.wishlist;

import gift.product.domain.Product;

public class WishlistSaveRequestDto {

    private Product product;

    public WishlistSaveRequestDto(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
