package gift.dto.wishlist;

import gift.domain.product.Product;
import gift.domain.Wishlist;

public class WishlistResponse {
    Long id;

    Long productId;
    String productName;
    String productImageUrl;
    Integer productPrice;

    public WishlistResponse(Wishlist wishlist) {
        Product product = wishlist.getProduct();
        this.id = wishlist.getId();
        this.productId = product.getId();
        this.productName = product.getName();
        this.productImageUrl = product.getImageUrl();
        this.productPrice = product.getPrice();
    }

    public Long getId() {
        return id;
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

    public String getProductImageUrl() {
        return productImageUrl;
    }
}
