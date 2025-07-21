package gift.service;

import gift.dto.OptionResponseDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponseDto saveProduct(ProductRequestDto requestDto);
    List<ProductResponseDto> findAllProducts();

    Page<ProductResponseDto> findAllProductPage(Pageable pageable);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    void deleteProduct(Long id);

    List<OptionResponseDto> findOptionsByProductId(Long id);


}
