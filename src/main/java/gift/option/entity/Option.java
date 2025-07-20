package gift.option.entity;

import gift.product.entity.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {}

    public Option(Long id, String name, int quantity, Product product) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Option(String name, int quantity, Product product) {
        this(null, name, quantity, product);
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public int getQuantity() {return quantity;}
    public Product getProduct() {return product;}
}
