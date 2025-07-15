package gift.dto.itemDto;

import gift.entity.Item;


public record ItemResponseDto(Long id, String name, Integer price, String imageUrl) {

    public static ItemResponseDto from(Item item) {
        return new ItemResponseDto(item.getId(), item.getName(), item.getPrice(), item.getImageUrl());
    }
}
