package gift.product.dto;

import gift.entity.Product;

public record ProductResponseDto (Long id, String name, Long price, String url){
    public ProductResponseDto(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getUrl());
    }
}
