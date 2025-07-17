package gift.product.entity;

import gift.product.dto.request.ProductModifyRequest;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long giftId;

    @Column(nullable = false)
    private String giftName;

    @Column(nullable = false)
    private Integer giftPrice;

    @Column(nullable = false)
    private String giftPhotoUrl;

    @Column(nullable = false)
    private boolean isKakaoMDAccepted = true;

    public Product(Long giftId, String giftName, Integer giftPrice, String giftPhotoUrl) {
        this.giftId = giftId;
        this.giftName = giftName;
        this.giftPrice = giftPrice;
        this.giftPhotoUrl = giftPhotoUrl;
    }

    public Product() {}

    public Long getId() {
        return id;
    }

    public Long getGiftId() {
        return giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public Integer getGiftPrice() {
        return giftPrice;
    }

    public String getGiftPhotoUrl() {
        return giftPhotoUrl;
    }

    public boolean getIsKakaoMDAccepted() {
        return isKakaoMDAccepted;
    }

    public boolean isGiftNameValid(){
        String pattern = "^[a-zA-Z0-9가-힣 ()\\[\\]\\+\\-\\&\\/\\_]*$";
        return giftName.matches(pattern);
    }

    public void isKakaoMessageInclude(){
        String censorshipWord = "카카오";
        if(giftName.contains(censorshipWord)){
            this.isKakaoMDAccepted = false;
        }
    }

    public void modifyProduct(ProductModifyRequest productModifyRequest) {
        if(productModifyRequest.giftId() != null && !Objects.equals(giftId, productModifyRequest.giftId())){
            this.giftId = productModifyRequest.giftId();
        }
        if(productModifyRequest.giftName() != null && !Objects.equals(giftName, productModifyRequest.giftName())){
            this.giftName = productModifyRequest.giftName();
        }
        if(productModifyRequest.giftPrice() != null && !Objects.equals(giftPrice, productModifyRequest.giftPrice())){
            this.giftPrice = productModifyRequest.giftPrice();
        }
        if(productModifyRequest.giftPhotoUrl() != null && !Objects.equals(giftPhotoUrl, productModifyRequest.giftPhotoUrl())){
            this.giftPhotoUrl = productModifyRequest.giftPhotoUrl();
        }
    }
}
