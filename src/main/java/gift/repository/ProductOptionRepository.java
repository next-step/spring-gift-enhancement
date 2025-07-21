package gift.repository;

import gift.entity.ProductOption;
import org.springframework.data.repository.CrudRepository;


public interface ProductOptionRepository extends CrudRepository<ProductOption, Long> {
    ProductOption saveAndFlush(ProductOption productOption);
}
