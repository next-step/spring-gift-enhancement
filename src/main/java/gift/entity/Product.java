package gift.entity;

import gift.dto.OptionRequestDto;
import gift.exception.DuplicateOptionNameException;
import gift.exception.InvalidEntityDataException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    protected Product() {}

    public Product(String name, BigDecimal price, String imageUrl, List<OptionRequestDto> optionRequestDtoList) {
        if (optionRequestDtoList == null || optionRequestDtoList.isEmpty()) {
            throw new InvalidEntityDataException("상품에는 최소 한 개 이상의 옵션이 필요합니다.");
        }
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;

        optionRequestDtoList.forEach(optionRequestDto -> {
            this.addOption(new Option(optionRequestDto.name(), optionRequestDto.quantity(), this));
        });
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void updateProduct(String name, BigDecimal price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void addOption(Option option) {
        boolean isNameDuplicate = this.options.stream()
                .anyMatch(existingOption -> existingOption.getName().equals(option.getName()));

        if (isNameDuplicate) {
            throw new DuplicateOptionNameException("이미 존재하는 옵션 이름입니다: " + option.getName());
        }
        this.options.add(option);
    }
}
