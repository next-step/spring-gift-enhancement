package gift.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gift.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestDto {

    @NotBlank(message = "상품명을 입력해야합니다")
    @Size(min = 1, max = 15, message = "상품명의 크기는 1에서 15사이여야 합니다")
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&/_]*$",
            message = "이름에는 ( ) [ ] + - & / _ 외의 특수문자를 사용할 수 없습니다."
    )
    private String name;

    @Positive(message = "가격은 양수여야 합니다.")
    @NotNull
    private Long price;

    @NotBlank
    private String imageUrl;

    @Valid
    @NotEmpty(message = "최소 1개 이상의 옵션이 필요합니다.")
    private List<OptionRequestDto> options = new ArrayList<>();


    @JsonProperty("kakaoWordAllow")
    private boolean kakaoWordAllow = false;


    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<OptionRequestDto> getOptions() {
        return options;
    }

    @JsonIgnore
    @AssertTrue(message = "'카카오'라는단어는담당 MD와 협의한 경우에만 사용할 수 있습니다.")
    public boolean isKakaoWordAllowed(){
        if (!kakaoWordAllow && name != null && name.contains("카카오")){
            return false;
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setKakaoWordAllow(boolean kakaoWordAllow) {
        this.kakaoWordAllow = kakaoWordAllow;
    }
    public void setOptions(List<OptionRequestDto> options) {
        this.options = options;
    }

    public Product toEntity() {
       return new Product(name, price, imageUrl);
    }
}
