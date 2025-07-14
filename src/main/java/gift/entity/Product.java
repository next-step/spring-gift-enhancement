package gift.entity;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import jakarta.persistence.*;


@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected Product() {}

    public Product(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }

    public void update(ProductRequest request) {
        this.name = request.name();
        this.price = request.price();
        this.imageUrl = request.imageUrl();
    }

    public ProductResponse toResponse() {
        return new ProductResponse(id, name, price, imageUrl);
    }
}