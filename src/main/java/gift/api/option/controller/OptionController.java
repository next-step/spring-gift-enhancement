package gift.api.option.controller;

import gift.api.option.dto.OptionRequestDto;
import gift.api.option.dto.OptionResponseDto;
import gift.api.option.service.OptionService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    public ResponseEntity<List<OptionResponseDto>> getOptionsForProduct(
            @PathVariable Long productId) {
        List<OptionResponseDto> options = optionService.getOptionsByProductId(productId);
        
        return ResponseEntity.ok(options);
    }

    @PostMapping
    public ResponseEntity<OptionResponseDto> addOptionToProduct(
            @PathVariable Long productId,
            @Valid @RequestBody OptionRequestDto requestDto) {
        OptionResponseDto responseDto = optionService.addOption(productId, requestDto);

        URI location = URI.create(
                String.format("/api/products/%d/options/%d", productId, responseDto.id()));

        return ResponseEntity.created(location).body(responseDto);
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<OptionResponseDto> updateOption(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            @Valid @RequestBody OptionRequestDto requestDto) {
        OptionResponseDto updatedOption = optionService.updateOption(productId, optionId,
                requestDto);

        return ResponseEntity.ok(updatedOption);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable Long productId,
            @PathVariable Long optionId) {
        optionService.deleteOption(productId, optionId);

        return ResponseEntity.noContent().build();
    }
}
