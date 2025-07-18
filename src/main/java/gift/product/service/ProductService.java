package gift.product.service;

import gift.product.domain.Product;
import gift.product.dto.ProductPatchRequestDto;
import gift.product.dto.ProductSaveRequestDto;
import gift.product.repository.ProductRepository;
import jakarta.persistence.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final EntityManager entityManager;

    public ProductService(ProductRepository productRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Product createProduct(ProductSaveRequestDto productSaveRequestDto) {
        Product product = new Product(productSaveRequestDto.getName(), productSaveRequestDto.getPrice(), productSaveRequestDto.getImageUrl());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
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
        return productRepository.save(new Product(product.getId(), productPatchRequestDto.getName(), productPatchRequestDto.getPrice(), productPatchRequestDto.getImageUrl()));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 ID가 존재하지 않습니다."));

        productRepository.delete(product);
    }
}
