package gift.product.service;

import gift.product.domain.Product;
import gift.product.dto.ProductPatchRequestDto;
import gift.product.dto.ProductSaveRequestDto;
import gift.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(ProductSaveRequestDto productSaveRequestDto) {
        Product product = new Product(productSaveRequestDto.getName(), productSaveRequestDto.getPrice(), productSaveRequestDto.getImageUrl());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 ID가 존재하지 않습니다."));
    }

    @Transactional
    public Product updateProduct(Long id, ProductPatchRequestDto productPatchRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("해당 ID가 존재하지 않습니다."));

        product.changeName(productPatchRequestDto.getName());
        product.changePrice(productPatchRequestDto.getPrice());
        product.changeImageUrl(productPatchRequestDto.getImageUrl());

        return product;
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 ID가 존재하지 않습니다."));

        productRepository.delete(product);
    }
}
