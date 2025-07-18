package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//
@Service
@Transactional
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }


    public Page<Product> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    public boolean update(Long id, Product product) {
        Product target = repo.findById(id).orElse(null);
        if (target == null) {
            return false;
        }
        target.setName(product.getName());
        target.setPrice(product.getPrice());
        target.setImageUrl(product.getImageUrl());
        return true;
    }

    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
