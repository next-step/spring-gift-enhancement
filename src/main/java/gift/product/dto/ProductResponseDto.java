package gift.product.dto;

import gift.product.Product;

public record ProductResponseDto (Long id, String name, Long price, String url){
    public ProductResponseDto(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getUrl());
    }
}
