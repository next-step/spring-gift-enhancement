package gift.entity;

import jakarta.persistence.*;

@Entity
public class ItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Item item;

    private String optionName;

    @Column(nullable = false)
    private Integer quantity;

    protected ItemOption() {
    }

}
