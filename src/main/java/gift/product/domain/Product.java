package gift.product.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private String imageUrl;

    protected Product() {}

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, Integer price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }
        if (newName.length() > 15) {
            throw new IllegalArgumentException("이름은 15자 이하만 가능합니다.");
        }
        this.name = newName;
    }
    public void changePrice(Integer newPrice) {
        if (newPrice == null) {
            throw new IllegalArgumentException("가격은 필수입니다.");
        }
        if (newPrice < 0) {
            throw new IllegalArgumentException("가격은 음수가 될 수 없습니다.");
        }
        this.price = newPrice;
    }

    public void changeImageUrl(String newImageUrl) {
        this.imageUrl = newImageUrl;
    }

    public Long getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}