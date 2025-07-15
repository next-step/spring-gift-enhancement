package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll(){
        return productRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
    }

    @Transactional
    public void update(Long id, Product product) {
        boolean updated = productRepository.updateById(
                id,
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        ) > 0;

        if (!updated) {
            throw new NoSuchElementException("해당 상품이 존재하지 않습니다.");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("해당 상품이 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }

}

