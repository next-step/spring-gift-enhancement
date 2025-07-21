package gift.controller.optionController;

import gift.dto.optionDto.OptionCreateDto;
import gift.dto.optionDto.OptionResponseDto;
import gift.entity.ItemOption;
import gift.service.optionService.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    public ResponseEntity<OptionResponseDto> addItemOption(@RequestBody OptionCreateDto optionCreateDto, @RequestParam Long itemId) {

        ItemOption itemOption = optionService.save(optionCreateDto,itemId);
        OptionResponseDto optionResponseDto = OptionResponseDto.from(itemOption);

        return new ResponseEntity<>(optionResponseDto, HttpStatus.CREATED);
    }
}
