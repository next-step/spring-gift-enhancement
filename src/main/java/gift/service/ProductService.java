package gift.service;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductResponse::new)
                .orElseThrow(() -> new ProductNotFoundException("해당 상품이 존재하지 않습니다."));
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest request) {
        Product product = new Product(request.name(), request.price(), request.imageUrl());
        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException(
                                "해당 ID의 상품이 존재하지 않아 업데이트할 수 없습니다: " + id));

        product.update(request.name(), request.price(), request.imageUrl());

        return new ProductResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("해당 ID의 상품이 존재하지 않아 삭제할 수 없습니다: " + id);
        }
        productRepository.deleteById(id);
    }
}
