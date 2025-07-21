package gift.dto.product;

import gift.domain.product.Product;

public record ProductManageResponse(Long id, String name, String imageUrl) {

    public static ProductManageResponse from(Product product) {
        return new ProductManageResponse(product.getId(), product.getName(), product.getImageUrl());
    }
}
