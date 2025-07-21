package gift.repository;

import gift.domain.product.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장")
    void test1() {
        Product product = new Product("감자칩", "image", 10000, 100);
        Product save = productRepository.save(product);

        assertThat(save.getId()).isNotNull();
        assertThat(save.getName()).isEqualTo("감자칩");
        assertThat(save.getImageUrl()).isEqualTo("image");
        assertThat(save.getPrice()).isEqualTo(10000);
        assertThat(save.getQuantity()).isEqualTo(100);
    }

    @Test
    @DisplayName("상품 수정")
    void test2() {
        Product product = new Product("감자칩", "image", 10000, 100);
        Product save = productRepository.save(product);

        em.flush();

        Product getProduct = productRepository.findById(save.getId()).get();
        getProduct.update("고구마칩", "image2", 20000, 10000);

        em.flush();
        em.clear();

        Product expected = productRepository.findById(save.getId()).get();

        assertThat(expected.getId()).isNotNull();
        assertThat(expected.getName()).isEqualTo("고구마칩");
        assertThat(expected.getImageUrl()).isEqualTo("image2");
        assertThat(expected.getPrice()).isEqualTo(20000);
        assertThat(expected.getQuantity()).isEqualTo(10000);
    }

    @Test
    @DisplayName("상품 조회")
    void test3() {
        Product product = new Product("감자칩", "image", 10000, 100);
        productRepository.save(product);

        em.flush();
        em.clear();

        Product getProduct = productRepository.findById(product.getId()).get();
        assertThat(getProduct.getId()).isNotNull();
        assertThat(getProduct.getName()).isEqualTo("감자칩");
        assertThat(getProduct.getImageUrl()).isEqualTo("image");
        assertThat(getProduct.getPrice()).isEqualTo(10000);
        assertThat(getProduct.getQuantity()).isEqualTo(100);
    }

    @Test
    @DisplayName("상품 삭제")
    void test4() {
        Product product = new Product("감자칩", "image", 10000, 100);
        productRepository.save(product);

        em.flush();
        em.clear();

        productRepository.delete(product);

        em.flush();

        Optional<Product> getProduct = productRepository.findById(product.getId());
        assertThat(getProduct).isEmpty();
    }

}
