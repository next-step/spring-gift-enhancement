package gift.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private Long price;
    private String imageUrl;
    private Long ownerId;

    protected Product() {

    }

    public Product(String name, Long price, String imageUrl) {
        this(null, name, price, imageUrl, null);
    }


    public Product(String name, Long price, String imageUrl, Long ownerId) {
        this(null, name, price, imageUrl, ownerId);
    }

    public Product(Long id, String name, Long price, String imageUrl, Long ownerId) {
        super(id);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", ownerId=" + ownerId +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product objProd)) return false;
        return Objects.equals(getId(), objProd.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getId());
    }
}
