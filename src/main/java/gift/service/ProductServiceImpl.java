package gift.service;

import gift.dto.OptionResponseDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OptionService optionService;

    public ProductServiceImpl(ProductRepository productRepository, OptionService optionService) {
        this.productRepository = productRepository;
        this.optionService = optionService;
    }

    @Override
    public ProductResponseDto saveProduct(ProductRequestDto requestDto) {
        Product product = requestDto.toEntity();
        optionService.saveOptions(product,requestDto.getOptions());
        Product saveProduct = productRepository.save(product);

        return new ProductResponseDto(saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice(), saveProduct.getImageUrl(), saveProduct.getOptions());
    }

    @Override
    public List<ProductResponseDto> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = allProducts.stream()
                .map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getOptions()))
                .collect(Collectors.toList());
        return productResponseDtoList;
    }


    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {

        Product product = findProductById(id);

        product.setName(requestDto.getName());
        product.setPrice(requestDto.getPrice());
        product.setImageUrl(requestDto.getImageUrl());
        product.clearOptions();
        optionService.saveOptions(product,requestDto.getOptions());
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getOptions());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponseDto> findAllProductPage(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        Page<ProductResponseDto> productResponseDtoPage = productPage.map(ProductResponseDto::from);

        return productResponseDtoPage;
    }

    @Override
    public List<OptionResponseDto> findOptionsByProductId(Long id) {
        Product product = findProductById(id);
        return product.getOptions().stream()
                .map(option -> new OptionResponseDto(
                        option.getId(),
                        option.getName(),
                        option.getQuantity()
                ))
                .toList();
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당상품을 찾을 수 없습니다. id = " + productId
                ));
    }
}
