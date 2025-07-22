package gift;

import gift.product.domain.Product;
import gift.product.domain.ProductOption;
import gift.product.repository.ProductOptionRepository;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
public class ProductOptionRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOptionRepository productOptionRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product("테스트 상품", 10000, "test_image.jpg");
        productRepository.save(product);
    }
    @Test
    void save() {
        ProductOption productOption = new ProductOption("option1", 100);
        Product product = productRepository.findById(1L).get();
        product.addOption(productOption);
        ProductOption actual = productOptionRepository.save(productOption);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("option1"),
                () -> assertThat(actual.getQuantity()).isEqualTo(100)
        );
    }
}