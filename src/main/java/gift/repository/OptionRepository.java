package gift.repository;

import gift.model.Option;
import gift.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    boolean existsByName(String name);

    List<Option> findAllByProduct(Product product);

    boolean existsByProductAndName(Product product, String name);
}
