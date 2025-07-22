package gift.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private boolean needsMdApproval;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, Integer price, String imageUrl,
        boolean needsMdApproval) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.needsMdApproval = needsMdApproval;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isNeedsMdApproval() {
        return needsMdApproval;
    }
}
