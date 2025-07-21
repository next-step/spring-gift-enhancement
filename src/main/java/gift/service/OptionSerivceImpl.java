package gift.service;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OptionSerivceImpl implements OptionService{

    private final OptionRepository optionRepository;
    public OptionSerivceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    @Transactional
    public void saveOptions(Product product, List<OptionRequestDto> optionRequestDtoList) {
        checkDuplicatedOptionName(optionRequestDtoList);

        for (OptionRequestDto optionRequestDto : optionRequestDtoList) {
            product.saveOption(optionRequestDto.toEntity());
        }

    }

    @Override
    @Transactional
    public OptionResponseDto sellOption(Long id, Integer sellQuantity) {
        Option option = findOptionById(id);
        if(sellQuantity > option.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");
        }
        option.sellOption(sellQuantity);
        return new OptionResponseDto(option.getId(), option.getName(), option.getQuantity());
    }

    @Override
    public void checkDuplicatedOptionName(List<OptionRequestDto> optionRequestDtoList) {
        Set<String> optionNames = optionRequestDtoList.stream()
                .map(OptionRequestDto::getName)
                .collect(Collectors.toSet());

        if (optionNames.size() != optionRequestDtoList.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품의 옵션이름이 중복이 될 수 없습니다.");
        }
    }

    private Option findOptionById(Long id) {
        return optionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 옵션을 찾을 수 없습니다."));
    }
}
