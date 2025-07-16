package gift.service;

import gift.common.exception.ProductNotFoundException;
import gift.domain.Product;
import gift.dto.product.CreateProductRequest;
import gift.dto.product.ProductManageResponse;
import gift.dto.product.UpdateProductRequest;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductManageService {

    private final ProductRepository productRepository;
    private static final int PAGE_SIZE = 10;

    public ProductManageService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductManageResponse> getAllProductsByOffset(int page) {
        return productRepository.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id").descending())).map(ProductManageResponse::from);
    }

    public Product saveProduct(CreateProductRequest request) {
        Product product = new Product(request.name(), request.imageUrl(), request.price(), request.quantity());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductManageResponse getProduct(Long id) {
        return ProductManageResponse.from(getById(id));
    }

    private Product getById(Long id) {
        Optional<Product> getProduct = productRepository.findById(id);
        return getProduct.orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, UpdateProductRequest request) {
        Product product = getById(id);
        product.update(request.name(),  request.imageUrl(), request.price(), request.quantity());
        return product;
    }
}
