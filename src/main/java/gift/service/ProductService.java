package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//
@Service
@Transactional
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }


    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    public Product update(Long id, Product product) {
        Product target = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
        target.setName(product.getName());
        target.setPrice(product.getPrice());
        target.setImageUrl(product.getImageUrl());
        return target;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
