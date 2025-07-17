package gift.item.controller;

import gift.common.dto.PageResponseDto;
import gift.common.exception.InvalidSortByException;
import gift.common.exception.InvalidSortDirectionException;
import gift.item.dto.ItemCreateDto;
import gift.item.dto.ItemResponseDto;
import gift.item.dto.ItemUpdateDto;
import gift.item.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@Validated
public class ItemController {

    private final ItemService itemService;

    private static final Set<String> ALLOWED_SORT_FIELDS =
        Set.of("name", "price", "id");

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> findItem(@PathVariable Long itemId) {
        ItemResponseDto dto = itemService.findItem(itemId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<ItemResponseDto>> findAll(
        @RequestParam(defaultValue = "1")
        @Positive(message = "페이지 인덱스는 양수이어야 합니다.")
        int page,
        @RequestParam(defaultValue = "10")
        @Positive(message = "페이지 사이즈는 양수이어야 합니다.")
        int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "desc") String direction
    ) {
        if (!direction.equalsIgnoreCase("asc") &&
            !direction.equalsIgnoreCase("desc")) {
            throw new InvalidSortDirectionException(direction);
        }
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortByException(sortBy);
        }

        PageResponseDto<ItemResponseDto> pagedDtos = itemService.findAll(
            page,
            size,
            sortBy,
            direction
        );
        return ResponseEntity.ok(pagedDtos);
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(
        @RequestBody @Valid ItemCreateDto itemCreateDto) {
        ItemResponseDto dto = itemService.createItem(itemCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(
        @PathVariable Long itemId,
        @RequestBody @Valid ItemUpdateDto itemUpdateDto
    ) {
        ItemResponseDto dto = itemService.updateItem(itemId, itemUpdateDto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
