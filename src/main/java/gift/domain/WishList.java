package gift.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long memberId;
//    private Long productId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    public WishList(Long id, Member member, Product product, int quantity) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public WishList() {}

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return member.getId();
    }

    public Long getProductId() {
        return product.getId();
    }


    public int getQuantity() {
        return quantity;
    }


    public void update(int quantity){
        this.quantity += quantity;

        if(this.quantity<0)     this.quantity=0;
    }
}
