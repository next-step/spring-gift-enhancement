package gift.repository;

import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product,Long> {

    @Modifying(clearAutomatically = true)
    @Query("""
    UPDATE Product p
    SET p.name = :name,
        p.price = :price,
        p.imageUrl = :imageUrl
    WHERE p.id = :id
""")
    int updateById(Long id, String name, int price, String imageUrl);
}

