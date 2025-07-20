package gift.option.service;

import gift.option.repository.OptionRepository;
import gift.product.repository.ProductRepository;

public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }
}
