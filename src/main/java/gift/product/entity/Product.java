package gift.product.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_kakao_approved_by_md", nullable = false)
    @ColumnDefault(value = "FALSE")
    private Boolean isKakaoApprovedByMd;

    public Product() {}

    public Product(Long id, String name, Long price, String imageUrl, Boolean isKakaoApprovedByMd) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isKakaoApprovedByMd = (isKakaoApprovedByMd == null) ? false : isKakaoApprovedByMd;
    }

    public Product(String name, Long price, String imageUrl, Boolean isKakaoApprovedByMd){
        this(null, name, price, imageUrl, isKakaoApprovedByMd);
    }

    public Long getId(){return id;}
    public String getName(){return name;}
    public Long getPrice(){return price;}
    public String getImageUrl(){return imageUrl;}
    public Boolean getIsKakaoApprovedByMd(){return isKakaoApprovedByMd;}

    public void updateProduct(String name,Long price,String imageUrl,Boolean isKakaoApprovedByMd){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isKakaoApprovedByMd = (isKakaoApprovedByMd == null) ? false : isKakaoApprovedByMd;
    }
}
