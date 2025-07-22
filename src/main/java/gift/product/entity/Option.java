package gift.product.entity;

import gift.product.dto.request.OptionCreateRequest;
import gift.shared.exception.option.OverQuantityException;
import jakarta.persistence.*;

import static gift.product.status.OptionStatus.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Option(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Option(OptionCreateRequest request){
        this.name = request.name();
        this.quantity = request.quantity();
    }

    protected Option() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void substract(Integer quantity){
        if(this.quantity - quantity < 0){
            throw new OverQuantityException(OVER_QUANTITY.getMessage());
        }
        this.quantity -= quantity;
    }
}
