package gift.option.controller;

import gift.option.service.OptionService;

public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }
}
