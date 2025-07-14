package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.dto.UpdateProductRequestDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.exception.EmailAlreadyExistsException;
import gift.exception.ResourceNotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(
                productRequestDto.name(),
                productRequestDto.price(),
                productRequestDto.imageUrl()
        );

        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(
                saveProduct.getId(),
                saveProduct.getName(),
                saveProduct.getPrice(),
                saveProduct.getImageUrl()
        );
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProduct() {
        return productRepository.findAll().stream()
            .map(p -> new ProductResponseDto(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getImageUrl()
            ))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 id 입니다."));

        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    @Transactional
    public ProductResponseDto updateProduct(UpdateProductRequestDto productRequestDto) {
        Product product = productRepository.findById(productRequestDto.id())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 id 입니다."));

        product.updateProduct(productRequestDto.name(), productRequestDto.price(), productRequestDto.imageUrl());

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }


    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("존재하지 않는 id 입니다.");
        }

        productRepository.deleteById(id);
    }
}
