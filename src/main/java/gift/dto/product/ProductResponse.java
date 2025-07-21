package gift.dto.product;

import gift.domain.product.Product;

public record ProductResponse(Long id, String name, String imageUrl) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl());
    }
}
