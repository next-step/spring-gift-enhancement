package gift.service.product;

import gift.dto.product.ProductOptionResponseDto;
import gift.dto.product.ProductResponseDto;
import gift.entity.Product;
import java.util.List;

public interface ProductOptionService {
    public ProductOptionResponseDto save(Long productId, String name, int quantity);

    public List<ProductOptionResponseDto> findAllById(Long productId);

    public ProductOptionResponseDto update(Long productOptionId, String name, int quantity);

    public void delete(Long productOptionId);
}
