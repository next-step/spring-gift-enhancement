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


    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public String getOptionName() {
        return optionName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemOption(Item item, String optionName, Integer quantity) {
        setItem(item);
        this.optionName = optionName;
        this.quantity = quantity;
    }

    public void setItem(Item item) {
        this.item = item;
        item.addOption(this);
    }
}
