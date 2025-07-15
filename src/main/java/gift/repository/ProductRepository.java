package gift.repository;

import gift.domain.Product;
import gift.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByStatusAndIsDeletedFalse(ProductStatus status);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.isDeleted = false")
    List<Product> findAllActive();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id = :id")
    void softDeleteById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Product p
        SET p.name = :#{#product.name},
            p.price = :#{#product.price},
            p.imageUrl = :#{#product.imageUrl},
            p.status = :#{#product.status},
            p.isDeleted = :#{#product.isDeleted}
        WHERE p.id = :id
    """)
    int updateById(Long id, Product product);
}

