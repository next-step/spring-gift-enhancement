package gift.product.service;

import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Page<ProductResponse> search(String name, Pageable pageable) {
        Page<Product> page = (name == null || name.isBlank())
                ? repository.findAll(pageable)
                : repository.searchByName(name, pageable);

        return page.map(ProductResponse::from);
    }

    public List<ProductResponse> findAllProducts() {
        return repository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = repository.getByIdOrThrow(id);;
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(request.getName(), request.getPrice(), request.getImgUrl());
        Product saved = repository.save(product);
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = repository.getByIdOrThrow(id);

        existing.updateName(request.getName());
        existing.updatePrice(request.getPrice());
        existing.updateImgUrl(request.getImgUrl());

        return ProductResponse.from(existing);
    }

    @Transactional
    public void delete(Long id) {
        Product existing = repository.getByIdOrThrow(id);
        repository.delete(existing);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}