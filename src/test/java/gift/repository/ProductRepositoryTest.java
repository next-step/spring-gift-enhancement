package gift.repository;

import gift.product.Product;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 상품추가_그리고_조회(){
        Product product = new Product("과자", 200L, "snack.png");
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("과자");
    }

    @Test
    public void 상품_삭제(){
        Product product = new Product("과자", 200L, "snack.png");
        productRepository.save(product);

        Product foundProduct = productRepository.findById(product.getId()).get();
        productRepository.deleteById(foundProduct.getId());
        Optional<Product> deletedProduct = productRepository.findById(product.getId());

        assertThat(deletedProduct).isNotPresent();
    }

}
