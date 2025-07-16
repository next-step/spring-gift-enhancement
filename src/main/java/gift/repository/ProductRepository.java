package gift.repository;

import gift.domain.Product;
import gift.dto.product.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.id < :lastId order by p.id desc")
    List<ProductResponse> findAllWithCursor(Long lastId, Pageable pageable);

}
