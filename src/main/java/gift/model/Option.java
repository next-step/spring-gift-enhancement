package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    public Option() {
    }

    public Option(Product product, String name, Integer quantity) {
        this(null, product, name, quantity);
    }


    public Option(Long id, Product product, String name, Integer quantity) {
        if (!validateQuantity(quantity)) {
            throw new IllegalArgumentException("옵션 수량은 1 이상 1억 미만이어야 합니다.");
        }

        this.id = id;
        this.product = product;
        this.name = name;
        this.quantity = quantity;
    }

    private boolean validateQuantity(Integer quantity) {
        if (quantity == null || quantity < 1 || quantity >= 100000000) {
            return false;
        }
        return true;
    }

    public void decreaseQuantity(int amount) {
        int newQuantity = this.quantity - amount;
        if (!validateQuantity(newQuantity)) {
            throw new IllegalArgumentException("변경할 옵션 수량은 1 이상 1억 미만이어야 합니다.");
        }

        this.quantity = newQuantity;
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

    public Integer getQuantity() {
        return quantity;
    }
}
