package gift.service;

import gift.dto.request.OptionRequestDto;
import gift.dto.response.OptionResponseDto;
import gift.entity.Option;
import java.util.List;

public interface OptionService {

    Option toOption(Long productId, OptionRequestDto request);

    void addOption(Long productId, OptionRequestDto optionRequestDto);

    void updateOption(Long productId, Long optionId, OptionRequestDto optionRequestDto);

    void deleteOption(Long optionId);

    List<OptionResponseDto> getOptions(Long productId);
}
