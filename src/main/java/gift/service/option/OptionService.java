package gift.service.option;

import gift.common.model.CustomAuth;
import gift.common.model.CustomPage;
import gift.entity.Option;
import org.springframework.data.domain.Pageable;

public interface OptionService {
    CustomPage<Option> findAllBy(Long productId, Pageable pageable);
    Option findBy(Long id, Long productId);
    Option create(Long productId, CustomAuth auth, String name, Long quantity);
    Option update(Long id, Long productId, CustomAuth auth, String name, Long quantity);
    Option increaseQuantityBy(Long id, Long productId, CustomAuth auth, Long quantity);
    Option decreaseQuantityBy(Long id, Long productId, CustomAuth auth, Long quantity);
    void deleteBy(Long id, Long productId, CustomAuth auth);
}
