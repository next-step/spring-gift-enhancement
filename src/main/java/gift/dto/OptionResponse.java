package gift.dto;

public class OptionResponse {

    private Long id;
    private Long productId;
    private String name;
    private Integer quantity;

    public OptionResponse(Long id, Long productId, String name, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public OptionResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
