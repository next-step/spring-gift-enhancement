package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.exception.InvalidProductNameException;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import gift.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    
    // 의존성 고정
    private final ProductRepository productRepository;
    private final List<String> forbiddenWords; // MD 협의 단어 목록

    // 의존성 주입
    public ProductService(ProductRepository productRepository, @Value("${forbidden_words}") String forbiddenWordsProp) {
        this.productRepository = productRepository;
        this.forbiddenWords = Arrays.stream(forbiddenWordsProp.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    @Transactional
    public ProductResponseDto findProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product", id));
        return new ProductResponseDto(product);
    }

    @Transactional
    public Page<ProductResponseDto> findAllProduct(Pageable pageable){
        return productRepository.findAll(pageable).map(ProductResponseDto::new);
    }

    @Transactional
    public ProductResponseDto saveProduct(ProductRequestDto requestDto){
        List<String> matched = forbiddenWords.stream().filter(requestDto.name()::contains).toList();
        if(!matched.isEmpty()){ // 금지 단어 포함돼있을 경우 예외 던지기
            throw new InvalidProductNameException(matched);
        }
        return new ProductResponseDto(productRepository.save(new Product(requestDto)));
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto){
        List<String> matched = forbiddenWords.stream().filter(requestDto.name()::contains).toList();
        if(!matched.isEmpty()){ // 금지 단어 포함돼있을 경우 예외 던지기
            throw new InvalidProductNameException(matched);
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product", id));
        product.update(requestDto.name(), requestDto.price(), requestDto.imageUrl());
        return new ProductResponseDto(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
