package gift.dto;

import jakarta.validation.constraints.NotNull;

public class CreateOptionResponse {

    @NotNull
    private final Long optionId;

    @NotNull
    private final Long productId;

    public CreateOptionResponse(Long optionId, Long productId) {
        this.optionId = optionId;
        this.productId = productId;
    }
}
