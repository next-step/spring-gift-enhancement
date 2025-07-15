package gift.controller.itemController;


import gift.dto.itemDto.ItemCreateDto;
import gift.dto.itemDto.ItemResponseDto;
import gift.dto.itemDto.ItemUpdateDto;
import gift.dto.itemDto.ResponseItems;
import gift.entity.Item;
import gift.service.itemService.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> addItems(
            @RequestBody @Valid ItemCreateDto dto
    ) {
        Item item = itemService.saveItem(dto);

        return new ResponseEntity<>(ItemResponseDto.from(item), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseItems> getItems(@RequestParam(required = false) String name, @RequestParam(required = false) Integer price) {
        List<Item> items = itemService.getItems(name, price);
        List<ItemResponseDto> itemList = ItemResponseDto.from(items);

        return ResponseEntity.ok(new ResponseItems(itemList));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItems(
            @RequestParam(required = false) String name
    ) {
        itemService.delete(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemUpdateDto> updateItems(
            @PathVariable Long id,
            @RequestBody @Valid ItemUpdateDto dto
    ) {
        ItemUpdateDto item = itemService.updateItem(id, dto);
        return ResponseEntity.ok(item);
    }
}
