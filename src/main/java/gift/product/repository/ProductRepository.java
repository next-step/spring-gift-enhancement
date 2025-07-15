package gift.product.repository;

import gift.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product save(String name, int price, String imageUrl);

    Optional<Product> findById(Long id);

    void update(Long id, String name, int price, String imageUrl);

    void deleteById(Long id);
}
