package gift.service;

import gift.common.code.CustomResponseCode;
import gift.common.exception.CustomException;
import gift.common.util.SortUtil;
import gift.dto.PageResponse;
import gift.dto.Pagination;
import gift.dto.ProductOptionRequest;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.dto.ProductSortField;
import gift.entity.Product;
import gift.entity.ProductOption;
import gift.repository.ProductRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(
            request.name(),
            request.price(),
            request.imageUrl()
        );

        if (request.options() == null || request.options().isEmpty()) {
            throw new CustomException(CustomResponseCode.OPTION_REQUIRED);
        }

        Set<String> nameSet = new HashSet<>();
        for (ProductOptionRequest optionRequest : request.options()) {
            if (!nameSet.add(optionRequest.name())) {
                throw new CustomException(CustomResponseCode.OPTION_DUPLICATED);
            }

            ProductOption option = ProductOption.of(
                optionRequest.name(),
                optionRequest.quantity(),
                product
            );
            product.addOption(option);
        }

        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAllProducts(Pagination pagination) {
        Sort sortCondition = SortUtil.createSort(
            pagination.getSort(),
            ProductSortField.allowedFields()
        );

        Pageable pageable = PageRequest.of(pagination.getPage() - 1,
            pagination.getSize(),
            sortCondition
        );

        Page<ProductResponse> page = productRepository
            .findAll(pageable)
            .map(ProductResponse::from);

        return PageResponse.from(page);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));

        return ProductResponse.from(product);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        boolean updated = productRepository.updateProduct(id, request.name(),
            request.price(), request.imageUrl()) > 0;

        if (!updated) {
            throw new CustomException(CustomResponseCode.NOT_FOUND);
        }

        Product updatedProduct = productRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomResponseCode.NOT_FOUND));

        return ProductResponse.from(updatedProduct);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException(CustomResponseCode.NOT_FOUND);
        }

        productRepository.deleteById(id);
    }
}
