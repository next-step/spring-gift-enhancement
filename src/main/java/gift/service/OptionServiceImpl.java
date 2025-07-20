package gift.service;

import gift.dto.request.OptionRequestDto;
import gift.dto.response.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.OptionNameDuplicationException;
import gift.exception.OptionNotFoundException;
import gift.repository.OptionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionServiceImpl(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }


    @Override
    public Option toOption(Long productId, OptionRequestDto request) {
        Product product = productService.getProduct(productId);
        Option option = new Option();
        option.setName(request.optionName());
        option.setProduct(product);
        option.setQuantity(request.quantity());
        return option;
    }

    @Override
    @Transactional
    public OptionResponseDto addOption(Long productId, OptionRequestDto optionRequestDto) {
        Option option = toOption(productId, optionRequestDto);
        if (optionRepository.existsByProductIdAndName(productId, option.getName())) {
            throw new OptionNameDuplicationException("이미 존재하는 옵션명입니다: " + option.getName());
        }
        Option storedOption = optionRepository.save(option);
        return new OptionResponseDto(storedOption);

    }

    @Override
    @Transactional
    public OptionResponseDto updateOption(Long productId, Long optionId,
        OptionRequestDto optionRequestDto) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException("옵션이 존재하지 않습니다"));
        if (optionRepository.existsByProductIdAndNameAndIdNot(productId, option.getName(),
            optionId)) {
            throw new OptionNameDuplicationException("이미 존재하는 옵션명입니다: " + option.getName());
        }
        option.setName(optionRequestDto.optionName());
        option.setQuantity(optionRequestDto.quantity());
        Option updatedOption = optionRepository.save(option);
        return new OptionResponseDto(updatedOption);
    }

    @Override
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }

    @Override
    public List<OptionResponseDto> getOptions(Long productId) {

        return optionRepository.findAllByProductId(productId)
            .stream()
            .map(OptionResponseDto::new)
            .toList();
    }
}
