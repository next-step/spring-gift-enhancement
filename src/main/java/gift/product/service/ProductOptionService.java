package gift.product.service;

import gift.product.dto.ProductOptionResponseDto;
import gift.product.repository.ProductOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;

    public ProductOptionService(ProductOptionRepository productOptionRepository) {
        this.productOptionRepository = productOptionRepository;
    }

    public List<ProductOptionResponseDto> getProductOptions(Long productId) {
        return productOptionRepository.findByProductId(productId)
                .stream()
                .map(ProductOptionResponseDto::from)
                .toList();
    }
}
