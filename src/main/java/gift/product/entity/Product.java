package gift.product.entity;


import gift.option.entity.Option;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    protected Product() {}

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
    public List<Option> getOptions(){return options;}

    public void updateProduct(String name,Long price,String imageUrl,Boolean isKakaoApprovedByMd){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isKakaoApprovedByMd = (isKakaoApprovedByMd == null) ? false : isKakaoApprovedByMd;
    }

    public void addOption(Option option){
        this.options.add(option);
        option.setProduct(this);
    }

    public boolean isDuplicateOptionName(String name){
        return this.options.stream()
                .anyMatch(option -> option.getName().equals(name));
    }
}
