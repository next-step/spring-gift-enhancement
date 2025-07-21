package gift.service;

import gift.dto.OptionResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OptionService {

    private final ProductRepository productRepository;

    public OptionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getOptionsForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + productId));

        return product.getOptions().stream()
                .map(OptionResponse::from)
                .toList();
    }
}
