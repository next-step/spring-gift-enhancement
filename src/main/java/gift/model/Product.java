package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Column(name = "needsMdApproval", nullable = false)
    private boolean needsMdApproval;

    @OneToMany
    private List<Option> options = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String name, Integer price, String imageUrl, boolean needsMdApproval) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.needsMdApproval = needsMdApproval;
    }

    public Product(String name, Integer price, String imageUrl, boolean needsMdApproval) {
        this(null, name, price, imageUrl, needsMdApproval);
    }

    public void update(String name, int price, String imageUrl, boolean needsMdApproval) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.needsMdApproval = needsMdApproval;
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

    public boolean getNeedsMdApproval() {
        return needsMdApproval;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
