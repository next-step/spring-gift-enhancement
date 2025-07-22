package gift.service;

import gift.dto.OptionResponse;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::new);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductResponse::new)
                .orElseThrow(() -> new ProductNotFoundException("해당 상품이 존재하지 않습니다."));
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest request) {
        validateUniqueOptionNames(request);

        Product product = new Product(request.name(), request.price(), request.imageUrl());

        request.options().stream()
                .map(optionRequest -> new Option(optionRequest.name(), optionRequest.quantity()))
                .forEach(product::addOption);

        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        validateUniqueOptionNames(request);

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException(
                                "해당 ID의 상품이 존재하지 않아 업데이트할 수 없습니다: " + id));

        product.update(request.name(), request.price(), request.imageUrl());
        product.getOptions().clear();

        request.options().stream()
                .map(optionRequest -> new Option(optionRequest.name(), optionRequest.quantity()))
                .forEach(product::addOption);

        Product updatedProduct = productRepository.save(product);
        return new ProductResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("해당 ID의 상품이 존재하지 않아 삭제할 수 없습니다: " + id);
        }
        productRepository.deleteById(id);
    }

    private void validateUniqueOptionNames(ProductRequest request) {
        Set<String> optionNames = new HashSet<>();
        for (var optionRequest : request.options()) {
            if (!optionNames.add(optionRequest.name())) {
                throw new IllegalArgumentException("옵션 이름은 중복될 수 없습니다: " + optionRequest.name());
            }
        }
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + productId));

        return product.getOptions().stream()
                .map(OptionResponse::from)
                .toList();
    }
}
