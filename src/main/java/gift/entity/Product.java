package gift.entity;

import gift.dto.ProductRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public Product() {
    }

    public Product(String name, Long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void updateFromProductRequestDTO(ProductRequestDTO dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.imageUrl = dto.getImageUrl();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}