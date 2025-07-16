package gift.entity;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private Long quantity;

    public Wishlist() {
    }

    public Wishlist(Long id, Member member, Product product, Long quantity) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(Long quantity) {
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }
}
