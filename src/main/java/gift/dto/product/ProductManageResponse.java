package gift.dto.product;

import gift.domain.Product;

public record ProductManageResponse(Long id, String name, Integer price, Integer quantity, String imageUrl) {

    public static ProductManageResponse from(Product product) {
        return new ProductManageResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getImageUrl());
    }
}
