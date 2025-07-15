package gift.repository;

import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return jpaRepository.findAllOrderById();
    }

    public Product saveProduct(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void updateProduct(Long id, String name, Long price, String imageUrl) {
        Product product = findProductById(id);
        product.update(name, price, imageUrl);
        jpaRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean findMdApprovedById(Long id) {
        return jpaRepository.findMdApprovedById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
