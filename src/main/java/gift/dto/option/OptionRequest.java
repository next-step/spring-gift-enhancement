package gift.dto.option;

// 이후 Option 객체 생성 등에 사용할 dto
public record OptionRequest(
    String name,
    Long productId,
    int quantity
) {}
