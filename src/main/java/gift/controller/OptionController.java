package gift.controller;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.service.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable Long productId) {
        return new ResponseEntity<>(optionService.getAllOptions(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OptionResponse> postOption(@PathVariable Long productId,
                                                     @RequestBody OptionRequest optionRequest) {
        return new ResponseEntity<>(optionService.createOption(productId, optionRequest), HttpStatus.CREATED);
    }
}
