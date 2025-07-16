package gift.service;

import gift.common.exception.ProductNotFoundException;
import gift.domain.Product;
import gift.dto.product.CreateProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private static final int PAGE_SIZE = 10;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(CreateProductRequest request) {
        Product product = new Product(request.name(), request.imageUrl(), request.price(), request.quantity());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProductsByCursor(Long cursor) {
        if (cursor == null) {
            return productRepository.findAll(PageRequest.of(0, PAGE_SIZE, Sort.by("id").descending())).map(ProductResponse::from).stream().toList();
        }
        return productRepository.findAllWithCursor(cursor, PageRequest.ofSize(PAGE_SIZE));
    }

    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        return getById(id);
    }

    public Product updateProduct(Long id, UpdateProductRequest request) {
        Product product = getById(id);
        product.update(request.name(), request.imageUrl(), request.price(), request.quantity());
        return product;
    }

    public void deleteProduct(Long id) {
        Product product = getById(id);
        productRepository.delete(product);
    }

    private Product getById(Long id) {
        Optional<Product> getProduct = productRepository.findById(id);
        return getProduct.orElseThrow(() -> new ProductNotFoundException(id));
    }
}
