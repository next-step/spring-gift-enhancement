package gift.service;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    public OptionResponse createOption(OptionRequest request) {
        if (optionRepository.existsByName(request.getName())) {
            throw new IllegalStateException("해당 옵션은 이미 존재합니다.");
        }
        Product product = productService.findById(request.getProductId());

        Option option = new Option(product, request.getName(), request.getQuantity());
        Option saved = optionRepository.save(option);

        return new OptionResponse(saved.getId(), saved.getProduct().getId(), saved.getName(),
            saved.getQuantity());
    }

    public List<OptionResponse> findAllByProductId(Long productId) {
        Product product = productService.getProduct(productId);
        List<Option> options = optionRepository.findAllByProduct(product);

        return options.stream().map(
            o -> new OptionResponse(o.getId(), o.getProduct().getId(), o.getName(),
                o.getQuantity())).collect(Collectors.toList());
    }

    public OptionResponse getOptionResponse(Long optionId) {
        Optional<Option> foundOption = optionRepository.findById(optionId);
        if (foundOption.isEmpty()) {
            throw new IllegalArgumentException("id: " + optionId + ". 해당 ID의 옵션이 존재하지 않습니다.");
        }

        Option option = foundOption.get();
        return new OptionResponse(option.getId(), option.getProduct().getId(), option.getName(),
            option.getQuantity());
    }

    public void decreaseQuantity(Long optionId, int amount) {
        Optional<Option> foundOption = optionRepository.findById(optionId);
        if (foundOption.isEmpty()) {
            throw new IllegalArgumentException("id: " + optionId + ". 해당 ID의 옵션이 존재하지 않습니다.");
        }
        Option option = foundOption.get();

        option.decreaseQuantity(amount);
    }

    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }


}
