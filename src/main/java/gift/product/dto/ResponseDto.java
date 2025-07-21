package gift.product.dto;

import gift.product.domain.Product;

import java.util.List;

public class ResponseDto {
    private String name;
    private int price;
    private String imageUrl;
    private List<ProductOptionResponseDto> options;

    public ResponseDto() {}

    public ResponseDto(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ResponseDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.options = product.getOptions()
                .stream()
                .map(ProductOptionResponseDto::new)
                .toList();
    }

    public String getName() { return name; }

    public Integer getPrice() { return price; }

    public String getImageUrl() { return imageUrl; }

    public List<ProductOptionResponseDto> getOptions() { return options; }
}
