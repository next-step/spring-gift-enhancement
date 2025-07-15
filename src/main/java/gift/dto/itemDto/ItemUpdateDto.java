package gift.dto.itemDto;

import gift.entity.Item;
import gift.validation.itemPolicy.ItemFieldValid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

@ItemFieldValid
public record ItemUpdateDto(@NotNull Long id, String name, @Min(0) Integer price,
                            @NotNull @Size(max = 255) String imageUrl, boolean useKakaoName) {
    public ItemUpdateDto(Optional<Item> item) {
        this(item.get().getId(), item.get().getName(), item.get().getPrice(), item.get().getImageUrl(), false);
    }

    public ItemUpdateDto(ItemDto item) {
        this(item.getId(), item.getName(), item.getPrice(), item.getImageUrl(), false);
    }
}
