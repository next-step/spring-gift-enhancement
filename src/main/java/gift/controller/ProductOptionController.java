package gift.controller;

import gift.dto.request.ProductOptionRequestDto;
import gift.service.ProductOptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
