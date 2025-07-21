package gift.service;

import gift.dto.request.ProductOptionRequestDto;
import gift.dto.response.ProductOptionResponseDto;
import gift.entity.Product;
import gift.entity.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    public ProductOptionService(ProductOptionRepository productOptionRepository, ProductRepository productRepository) {
        this.productOptionRepository = productOptionRepository;
        this.productRepository = productRepository;
    }


    public  void addOption(Long productId, ProductOptionRequestDto dto) {
        boolean exists = productOptionRepository.existsByProductIdAndName(productId, dto.getName());
        if (exists) {
            throw new IllegalArgumentException("이미 동일한 이름의 옵션이 존재합니다.");
        }


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));

        ProductOption option = new ProductOption(dto.getName(), dto.getQuantity(), product);
         productOptionRepository.save(option);
    }

    public List<ProductOptionResponseDto> getOptionsByProductId(Long productId) {
        return productOptionRepository.findByProductId(productId).stream()
                .map(ProductOptionResponseDto::from)
                .toList();
    }
}
