package gift.entity;

import gift.product.dto.ProductResponseDto;
import gift.product.dto.ProductUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private String url;

    public Product() {
    }

    public Product(Long id, String name, Long price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public ProductResponseDto toProductResponseDto() {
        return new ProductResponseDto(this);
    }

    public void update(Long id, ProductUpdateRequestDto requestDto) {
        this.id = id;
        this.name = requestDto.name();
        this.price = requestDto.price();
        this.url = requestDto.url();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}