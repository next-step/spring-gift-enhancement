package gift.api.option.service;

import gift.api.option.domain.Option;
import gift.api.option.dto.OptionRequestDto;
import gift.api.option.dto.OptionResponseDto;
import gift.api.option.repository.OptionRepository;
import gift.api.product.domain.Product;
import gift.api.product.repository.ProductRepository;
import gift.exception.conflict.OptionNameDuplicateException;
import gift.exception.notfound.OptionNotFoundException;
import gift.exception.notfound.ProductNotFoundException;
import gift.exception.option.InvalidOptionAccessException;
import gift.exception.option.OptionPolicyException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponseDto> getOptionsByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        return optionRepository.findByProductId(productId).stream()
                .map(OptionResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OptionResponseDto addOption(Long productId, OptionRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        optionRepository.findByProductAndName(product, requestDto.name()).ifPresent(option -> {
            throw new OptionNameDuplicateException(requestDto.name());
        });

        Option newOption = new Option(requestDto.name(), requestDto.quantity(), product);
        Option savedOption = optionRepository.save(newOption);

        return OptionResponseDto.from(savedOption);
    }

    @Transactional
    public OptionResponseDto updateOption(Long productId, Long optionId,
            OptionRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException(optionId));

        if (!option.getProduct().getId().equals(productId)) {
            throw new InvalidOptionAccessException("해당 상품에 속한 옵션이 아닙니다.");
        }

        // 수정하려는 이름이 현재 이름과 다른 경우, 기존 옵션 이름과 중복 검사
        if (!option.getName().equals(requestDto.name())) {
            optionRepository.findByProductAndName(product, requestDto.name()).ifPresent(opt -> {
                throw new OptionNameDuplicateException(requestDto.name());
            });
        }

        option.update(requestDto.name(), requestDto.quantity());

        return OptionResponseDto.from(option);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException(optionId));

        if (!option.getProduct().getId().equals(productId)) {
            throw new InvalidOptionAccessException("해당 상품에 속한 옵션이 아닙니다.");
        }

        if (option.getProduct().getOptions().size() <= 1) {
            throw new OptionPolicyException("상품에는 최소 1개의 옵션이 존재해야 합니다.");
        }

        // 부모의 관리 목록에서 자식을 빼는 방식
        product.getOptions().remove(option);
    }
}
