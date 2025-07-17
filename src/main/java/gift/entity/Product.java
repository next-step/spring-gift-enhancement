package gift.entity;

import gift.dto.ProductResponse;
import gift.entity.vo.Money;
import gift.entity.vo.ProductName;
import jakarta.persistence.*;

@Entity
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductName name;

    @Embedded
    private Money price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected Product() {}

    public Product(ProductName name, Money price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public String getName() { return name.getValue(); }
    public Integer getPrice() { return price.getValue(); }
    public String getImageUrl() { return imageUrl; }

    public void update(ProductName name, Money price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductResponse toResponse() {
        return new ProductResponse(id, this.getName(), this.getPrice(), imageUrl);
    }
}