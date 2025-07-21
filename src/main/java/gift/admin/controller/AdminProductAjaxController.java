package gift.admin.controller;

import gift.product.dto.ProductResponseDto;
import gift.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/products")
public class AdminProductAjaxController {

    private final ProductService productService;

    public AdminProductAjaxController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ProductResponseDto> getProductDetail(@PathVariable Long id) {
        ProductResponseDto product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }
}
