package gift.option.service;

import gift.exception.ProductNotFoundException;
import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.entity.Option;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
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

    @Transactional
    public OptionResponseDto addOption(Long productId, OptionRequestDto optionRequestDto) {
        Product product = getProduct(productId);

        if (product.isDuplicateOptionName(optionRequestDto.name())) {
            throw new IllegalArgumentException(optionRequestDto.name() + "해당 옵션명은 이미 존재합니다.");
        }

        Option option = new Option(optionRequestDto.name(),
                optionRequestDto.quantity(),
                product);

        product.addOption(option);

        return OptionResponseDto.from(optionRepository.save(option));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
