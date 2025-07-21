package gift.dto;

import gift.entity.Option;
import gift.entity.Product;

import java.util.List;

public class ProductResponseDto {

    private Long id;
    private String name;
    private long price;
    private String imageUrl;
    private List<Option> options;

    public ProductResponseDto(Long id, String name, long price, String imageUrl, List<Option> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.options = options;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Option> getOptions() {
        return options;
    }

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getOptions());
    }


}
