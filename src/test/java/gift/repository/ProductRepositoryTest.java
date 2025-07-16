package gift.repository;

import gift.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 및 ID로 조회 테스트")
    void saveAndFindById() {
        // given
        Product expected = new Product("테스트 상품", 10000, "test.jpg");

        // when
        Product savedProduct = productRepository.save(expected);
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // then
        assertAll(
                () -> assertThat(savedProduct.getId()).isNotNull(),
                () -> assertThat(foundProduct).isNotNull(),
                () -> assertThat(foundProduct.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("모든 상품 조회 테스트")
    void findAll() {
        // given
        productRepository.save(new Product("상품1", 100, "1.jpg"));
        productRepository.save(new Product("상품2", 200, "2.jpg"));

        // when
        List<Product> products = productRepository.findAll();

        // then
        assertThat(products).hasSize(2);
    }
}