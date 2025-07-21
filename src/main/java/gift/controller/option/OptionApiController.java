package gift.controller.option;

import gift.dto.option.OptionResponse;
import gift.service.option.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/options")
public class OptionApiController {
    private final OptionService optionService;

    public OptionApiController(OptionService optionService) {
        this.optionService = optionService;
    }

    // option 조회 메서드: 이후 관리자 기능으로 전환 예정
    @GetMapping("/{option_id}")
    public ResponseEntity<OptionResponse> getOptionById(
        @PathVariable(name = "option_id") Long optionId
    ){
        return ResponseEntity.status(HttpStatus.OK)
            .body(optionService.getOptionById(optionId));
    }

}
