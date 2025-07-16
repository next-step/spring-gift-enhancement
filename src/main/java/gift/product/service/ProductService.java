package gift.product.service;

import gift.entity.Product;
import gift.product.dto.ProductAddRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.dto.ProductUpdateRequestDto;

import java.util.List;

public interface ProductService {
    public void addProduct(ProductAddRequestDto requestDto);
    public ProductResponseDto findProductById(Long id);
    public List<ProductResponseDto> findAllProduct();
    public void updateProductById(Long id, ProductUpdateRequestDto requestDto);
    public void deleteProductById(Long id);
    public void validateProductName(String name, String viewName);

    Product findProductByIdOrElseThrow(Long id);
}
