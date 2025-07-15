package gift.repository.product;


import gift.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Product p set p.name = :#{#target.name}, p.price = :#{#target.price}, p.imageUrl = :#{#target.imageUrl} where p.id = :id")
    int update(@Param("id") Long productId, @Param("target") Product product);
}