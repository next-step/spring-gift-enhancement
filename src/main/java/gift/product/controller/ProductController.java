package gift.product.controller;

import gift.member.exception.InvalidMemberException;
import gift.product.dto.ProductAddRequestDto;
import gift.product.dto.ProductUpdateRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.exception.InvalidProductException;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Valid @RequestBody ProductAddRequestDto requestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidProductException(getDefaultMessage(bindingResult));
        }
        productService.addProduct(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(
            @PathVariable Long id
    ) {
        ProductResponseDto responseDto = productService.findProductById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductById(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequestDto requestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidProductException(getDefaultMessage(bindingResult));
        }
        productService.updateProductById(id, requestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(
            @PathVariable Long id
    ) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String getDefaultMessage(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError == null || fieldError.getDefaultMessage() == null) {
            throw new InvalidMemberException("잘못된 요청입니다.");
        }
        return fieldError.getDefaultMessage();
    }
}
