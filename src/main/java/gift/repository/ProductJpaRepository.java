package gift.repository;

import gift.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.mdApproval.approved FROM Product p WHERE p.id = :id")
    boolean findMdApprovedById(@Param("id") Long id);

    @Query("SELECT p FROM Product p ORDER BY p.id")
    List<Product> findAllOrderById();
}