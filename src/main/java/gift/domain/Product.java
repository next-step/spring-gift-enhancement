package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    @Column(nullable = false)
    private ProductStatus status;

    @Column(nullable = false)
    private boolean isDeleted;

    public Product() {
    }

    public Product(Long id, String name, int price, String imageUrl, ProductStatus status, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.isDeleted = isDeleted;
    }

    public Product(String name, int price, String imageUrl, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Product(String name, int price, String imageUrl, ProductStatus status, boolean isDeleted) {
        this(null, name, price, imageUrl,status, false);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setId(Long id){
        this.id = id;
    }

}