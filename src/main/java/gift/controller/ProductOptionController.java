package gift.controller;

import gift.dto.request.ProductOptionRequestDto;
import gift.dto.response.ProductOptionResponseDto;
import gift.service.ProductOptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{id}/options")
public class ProductOptionController {
    private final ProductOptionService productOptionService;

    public ProductOptionController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @PostMapping
    public ResponseEntity<Void> addOption(@PathVariable Long id, @RequestBody @Valid ProductOptionRequestDto dto) {

        productOptionService.addOption(id,dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping
    public ResponseEntity<List<ProductOptionResponseDto>> getOptions(@PathVariable Long id) {
        return ResponseEntity.ok(productOptionService.getOptionsByProductId(id));
    }

}
