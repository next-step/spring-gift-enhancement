package gift.service;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.DuplicateOptionNameException;
import gift.exception.InvalidEntityDataException;
import gift.exception.ResourceNotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<OptionResponseDto> getOptions(Long productId) {
        Product product = findProductById(productId);

        return product.getOptions().stream()
                .map(option -> new OptionResponseDto(
                        option.getId(),
                        option.getName(),
                        option.getQuantity())
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public OptionResponseDto addOption(Long productId, OptionRequestDto optionRequestDto) {
        Product product = findProductById(productId);

        Option option = new Option(
                optionRequestDto.name(),
                optionRequestDto.quantity(),
                product
        );

        product.addOption(option);

        Option savedOption = optionRepository.save(option);
        return new OptionResponseDto(
                savedOption.getId(),
                savedOption.getName(),
                savedOption.getQuantity()
        );
    }

    @Transactional
    public OptionResponseDto updateOption(Long productId, Long optionId, OptionRequestDto optionRequestDto) {
        Product product = findProductById(productId);
        Option option = findOptionById(optionId);

        // 해당 상품에 존재하는 옵션이 맞는지 확인
        if (!option.getProduct().getId().equals(product.getId())) {
            throw new ResourceNotFoundException("해당 상품에 속한 옵션이 아닙니다.");
        }

        // 옵션을 수정하기 전에 이름이 중복되지 않는지 확인
        if (!option.getName().equals(optionRequestDto.name())) {
            boolean isDuplicate = product.getOptions().stream()
                    .anyMatch(opt -> opt.getName().equals(optionRequestDto.name()));
            if (isDuplicate) {
                throw new DuplicateOptionNameException("이미 존재하는 옵션 이름입니다.");
            }
        }

        option.updateOption(optionRequestDto.name(), optionRequestDto.quantity());

        return new OptionResponseDto(
                option.getId(),
                option.getName(),
                option.getQuantity()
        );
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        Product product = findProductById(productId);
        Option option = findOptionById(optionId);

        // 해당 상품에 존재하는 옵션이 맞는지 확인
        if (!option.getProduct().getId().equals(product.getId())) {
            throw new ResourceNotFoundException("해당 상품에 속한 옵션이 아닙니다.");
        }

        if (product.getOptions().size() <= 1) {
            throw new InvalidEntityDataException("상품에는 최소 한 개의 옵션이 존재해야 하므로 삭제할 수 없습니다.");
        }

        optionRepository.delete(option);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("상품을 찾을 수 없습니다. ID: " + productId));
    }
    private Option findOptionById(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("옵션을 찾을 수 없습니다. ID: " + optionId));
    }

    @Transactional
    public void subtractQuantity(Long optionId, int amount) {
        Option option = findOptionById(optionId);

        option.subtractQuantity(amount);
    }
}
