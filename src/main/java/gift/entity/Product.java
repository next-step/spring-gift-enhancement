package gift.entity;

import gift.domain.product.MdApprovalStatus;
import gift.domain.product.ProductName;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductName name;

    private Long price;

    private String imageUrl;

    @Embedded
    @AttributeOverride(name = "approved", column = @Column(name = "md_approved"))
    private MdApprovalStatus mdApproval;

    protected Product() {
    }

    public Product(String name, Long price, String imageUrl) {
        this.name = new ProductName(name);
        this.price = price;
        this.imageUrl = imageUrl;
        this.mdApproval = MdApprovalStatus.of(name);
    }

    public Product(String name, long price, String imageUrl, MdApprovalStatus approved) {
        this.name = new ProductName(name);
        this.price = price;
        this.imageUrl = imageUrl;
        this.mdApproval = approved;
    }

    public void update(String name, Long price, String imageUrl) {
        this.name = new ProductName(name);
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isApproved() {
        return mdApproval != null && mdApproval.isApproved();
    }

    public void setId(Long productId) {
        this.id = productId;
    }
}