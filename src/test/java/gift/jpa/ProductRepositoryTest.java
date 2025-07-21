package gift.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.entity.Product;
import gift.entity.ProductOption;
import gift.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product("test", 1, 1, "test");
        ProductOption option = new ProductOption("option1", 1, expected);

        expected.addOption(option);

        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void findById() {
        Product expected = new Product("example1", 4700, 1, "https://www.starbucks.co.kr/index.do");
        ProductOption option = new ProductOption("option1", 1, expected);

        Product actual = productRepository.findById(1L).get();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void update() {
        Product origin = new Product("test", 1, 1, "test");
        Product expected = new Product("test2", 2, 1, "test2");

        ProductOption option = new ProductOption("option1", 1, expected);

        origin.addOption(option);

        Product beforeProduct = productRepository.save(origin);
        beforeProduct.change(expected.getName(), expected.getPrice(), expected.getImageUrl());

        Product actual = productRepository.findById(4L).get();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void existsById_success() {
        Boolean expected = productRepository.existsById(1L);
        assertThat(expected).isEqualTo(true);
    }

    @Test
    void existsById_fail() {
        Boolean expected = productRepository.existsById(999L);
        assertThat(expected).isEqualTo(false);
    }

    @Test
    void deleteById() {
        Product expected = new Product("test", 1, 1, "test");
        ProductOption option = new ProductOption("option1", 1, expected);

        expected.addOption(option);

        Product actual = productRepository.save(expected);
        productRepository.deleteById(actual.getId());
        assertThat(productRepository.existsById(actual.getId())).isEqualTo(false);
    }
}
