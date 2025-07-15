package gift.repository;

import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    void deleteByUserAndProduct(User user, Product product);

    boolean existsByUserAndProduct(User user, Product product);

    List<Wish> findAllByUser(User user);
}