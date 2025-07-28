package gift.model;


import jakarta.persistence.*;

@Entity
@Table(
        name = "product_option",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "name"})
)
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    protected ProductOption() {}

    public ProductOption(Product product, String name, Integer quantity) {
        this.product = product;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void decreaseQuantity(int amount) {
        if (quantity < amount) {
            throw new IllegalStateException("재고 부족: 현재 수량 = " + quantity);
        }
        this.quantity -= amount;
    }
    public void increaseQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("증가 수량은 음수일 수 없습니다.");
        }
        this.quantity += amount;
    }

}