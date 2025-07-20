package gift.option.controller;

import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    public ResponseEntity<OptionResponseDto> addOption(
            @PathVariable Long productId,
            @RequestBody @Valid OptionRequestDto optionRequestDto) {
        OptionResponseDto optionResponseDto = optionService.addOption(productId, optionRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(optionResponseDto);
    }

    public ResponseEntity<List<OptionResponseDto>> getOptions(
            @PathVariable Long productId) {
        List<OptionResponseDto> options = optionService.getOptions(productId);

        return ResponseEntity.ok(options);
    }
}
