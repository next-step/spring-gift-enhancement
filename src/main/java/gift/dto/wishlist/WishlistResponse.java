package gift.dto.wishlist;

import gift.domain.Product;
import gift.domain.Wishlist;

public class WishlistResponse {
    Long productId;
    String productName;
    Integer productPrice;

    public WishlistResponse(Wishlist wishlist) {
        Product product = wishlist.getProduct();
        this.productId = product.getId();
        this.productName = product.getName();
        this.productPrice = product.getPrice();
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }
}
