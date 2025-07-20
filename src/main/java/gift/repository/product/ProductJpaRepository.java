package gift.repository.product;

import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    // Jpa에서 Page<Product> findAll(Pageable pageable) 메서드를 이미 제공해 주고 있음.
}
