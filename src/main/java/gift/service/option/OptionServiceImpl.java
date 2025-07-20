package gift.service.option;

import gift.common.exception.AccessDeniedException;
import gift.common.mapper.ModelMapper;
import gift.common.model.CustomAuth;
import gift.common.model.CustomPage;
import gift.entity.Option;
import gift.entity.Product;
import gift.entity.UserRole;
import gift.repository.option.OptionRepository;
import gift.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class OptionServiceImpl implements OptionService{
    private static final Integer ADMIN_PRIORITY = UserRole.ROLE_ADMIN.getPriority();
    private static final Logger log = LoggerFactory.getLogger(OptionServiceImpl.class);

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionServiceImpl(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    private void validateAuthorization(CustomAuth auth, Product product) {
        if (auth.role().getPriority() >= ADMIN_PRIORITY) {
            log.info("관리자 권한으로 옵션 검증을 건너뜁니다.");
            return;
        }
        if (!product.getOwner().getId().equals(auth.userId())) {
            log.error("허용되지 않은 접근: 사용자 ID {}, 소유자 ID {}",auth.userId(), product.getOwner());
            throw new AccessDeniedException("허용되지 않은 접근입니다. 사용자 ID: " + auth.userId() + ", 소유자 ID: " + product.getOwner());
        }
    }


    private Option updateAndReturn(Option option, String name, Long quantity) {
        if (name != null) {
            if (optionRepository.existsByNameAndProductId(name, option.getProduct().getId())) {
                throw new DuplicateKeyException("이미 존재하는 옵션 이름입니다. name: " + name + ", productId: " + option.getProduct().getId());
            }
            option.setName(name);
        }
        if (quantity != null) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("수정될 수량은 0보다 커야 합니다.");
            }
            if (quantity >= 1_00_000_000L) {
                throw new IllegalArgumentException("수정될 수량은 10억 미만이어야 합니다.");
            }
            option.setQuantity(quantity);
        }
        return optionRepository.save(option);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPage<Option> findAllBy(Long productId, Pageable pageable) {
        if (!productService.existsById(productId)) {
            throw new NoSuchElementException("존재하지 않는 제품입니다. productId: " + productId);
        }
        return ModelMapper.toCustomPage(optionRepository.findAllByProductId(productId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Option findBy(Long id, Long productId) {
        if (!optionRepository.existsByIdAndProductId(id, productId)) {
            throw new NoSuchElementException("존재하지 않는 옵션입니다. id: " + id + ", productId: " + productId);
        }
        return optionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 옵션입니다. id: " + id));
    }

    @Override
    @Transactional
    public Option create(Long productId, CustomAuth auth, String name, Long quantity) {
        Product product = productService.findById(productId);
        validateAuthorization(auth, product);
        if (optionRepository.existsByNameAndProductId(name, productId)) {
            throw new DuplicateKeyException("이미 존재하는 옵션 이름입니다. name: " + name + ", productId: " + productId);
        }
        return optionRepository.save(new Option(name, quantity, product));
    }

    @Override
    @Transactional
    public Option update(Long id, Long productId, CustomAuth auth, String name, Long quantity) {
        Product product = productService.findById(productId);
        validateAuthorization(auth, product);
        Option existingOption = findBy(id, productId);
        return updateAndReturn(existingOption, name, quantity);

    }

    @Override
    @Transactional
    public Option increaseQuantityBy(Long id, Long productId, CustomAuth auth, Long quantity) {
        Product product = productService.findById(productId);
        validateAuthorization(auth, product);
        Option existingOption = findBy(id, productId);
        return updateAndReturn(existingOption, null, existingOption.getQuantity() + quantity);
    }

    @Override
    @Transactional
    public Option decreaseQuantityBy(Long id, Long productId, CustomAuth auth, Long quantity) {
        Product product = productService.findById(productId);
        validateAuthorization(auth, product);
        Option existingOption = findBy(id, productId);
        return updateAndReturn(existingOption, null, existingOption.getQuantity() - quantity);

    }

    @Override
    @Transactional
    public void deleteBy(Long id, Long productId, CustomAuth auth) {
        Product product = productService.findById(productId);
        var productOptions = product.getOptions();
        validateAuthorization(auth, product);

        if (productOptions.size() <= 1) {
            throw new IllegalArgumentException("최소 하나 이상의 옵션이 필요합니다.");
        }
        if (productOptions.removeIf(option -> option.getId().equals(id))) {
            optionRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("존재하지 않는 옵션입니다. id: " + id + ", productId: " + productId);
        }
    }
}
