package gift.service.option;

import gift.domain.Option;
import gift.dto.option.OptionSubtractRequest;
import gift.dto.option.OptionResponse;
import gift.global.exception.CustomException;
import gift.global.exception.ErrorCode;
import gift.repository.option.OptionJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
    private final OptionJpaRepository optionRepository;

    public OptionService(OptionJpaRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public OptionResponse subtractOption(OptionSubtractRequest optionSubtractRequest){
        Option option = optionRepository.findByProductId(optionSubtractRequest.productId())
                .orElseThrow(()-> CustomException.from(ErrorCode.NOT_EXISTS));
        option.subtractQuantity(optionSubtractRequest.quantity());

        return OptionResponse.from(option);
    }
}
