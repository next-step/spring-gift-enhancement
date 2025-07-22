package gift.repository.option;

import gift.domain.Option;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByProductId(Long productId);

    boolean existsByProductIdAndName(Long productId, String name);

    List<Option> findAllByProductId(Long productId);
}
