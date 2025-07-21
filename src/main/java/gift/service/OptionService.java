package gift.service;

import gift.dto.OptionRequestDTO;
import gift.dto.OptionResponseDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
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
    public List<OptionResponseDTO> getOptionsByProductId(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream()
            .map(option -> new OptionResponseDTO(option.getId(), option.getName(),
                option.getQuantity()))
            .collect(Collectors.toList());
    }

    @Transactional
    public OptionResponseDTO addOption(Long productId, OptionRequestDTO optionRequestDTO) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        Option option = new Option(optionRequestDTO.name(), optionRequestDTO.quantity(), product);
        Option savedOption = optionRepository.save(option);
        return new OptionResponseDTO(savedOption.getId(), savedOption.getName(),
            savedOption.getQuantity());
    }

    @Transactional
    public void updateOption(Long productId, Long optionId, OptionRequestDTO optionRequestDTO) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

        if (!option.getProduct().getId().equals(product.getId())) {
            throw new IllegalArgumentException("상품에 해당 옵션이 존재하지 않습니다.");
        }

        option.setName(optionRequestDTO.name());
        option.setQuantity(optionRequestDTO.quantity());
        optionRepository.save(option);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

        if (!option.getProduct().getId().equals(product.getId())) {
            throw new IllegalArgumentException("상품에 해당 옵션이 존재하지 않습니다.");
        }

        if (optionRepository.findByProductId(productId).size() == 1) {
            throw new IllegalArgumentException("상품에는 최소 하나 이상의 옵션이 있어야 합니다.");
        }

        optionRepository.delete(option);
    }

    @Transactional
    public void subtractQuantity(Long optionId, int quantity) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));
        option.subtractQuantity(quantity);
        optionRepository.save(option);
    }
}

