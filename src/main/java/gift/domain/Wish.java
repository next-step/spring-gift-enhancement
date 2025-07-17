package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "wishes",
        uniqueConstraints = @UniqueConstraint(name = "uk_wishes_members_products", columnNames = {"member_id", "product_id"})
)

public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_wishes_members"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_wishes_products"))
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public Wish(){

    }

    public Wish(Member member, Product product, int quantity){
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() { return member;}

    public Product getProduct(){
        return product;
    }

    public int getQuantity(){
        return quantity;
    }

}