package gift.product.repository;

import gift.product.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ProductJpaRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository repository;

    public ProductJpaRepositoryAdapter(ProductJpaRepository repository){
        this.repository = repository;
    }

    @Override
    public Product save(String name, int price, String imageUrl) {
        return repository.save(new Product(null, name, price, imageUrl));
    }
    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }
    @Override public List<Product> findAll() {
        return repository.findAll();
    }
    @Override
    public void update(Long id, String name, int price, String imageUrl) {
        repository.save(new Product(id, name, price, imageUrl));
    }
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
