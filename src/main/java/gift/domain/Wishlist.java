package gift.domain;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @Column(nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @Column(nullable = false)
    private Product product;

    public Long getId() {
        return id;
    }

    public Wishlist() {
    }
}
