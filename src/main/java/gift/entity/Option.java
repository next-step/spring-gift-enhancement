package gift.entity;

import gift.exception.OutOfQuantityException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "options", uniqueConstraints = {
        @UniqueConstraint(
                name = "UniqueOptionName",
                columnNames = {"name", "product_id"}
        )
})
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {}

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void updateOption(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void subtractQuantity(int amount) {
        if (this.quantity < amount) {
            throw new OutOfQuantityException("수량이 부족합니다. (남은 수량: " + this.quantity + ")");
        }

        this.quantity -= amount;
    }
}
