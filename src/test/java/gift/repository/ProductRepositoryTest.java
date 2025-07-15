package gift.repository;

import gift.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 레퍼지토리에 상품이 정상적으로 저장되는지 테스트")
    void save() {
        Product expectedProduct = productRepository.save(
                new Product(
                        "테스트",
                        10000L,
                        "https://test.com",
                        true,
                        ""
                ));

        Product actualProduct = productRepository.save(expectedProduct);

        assertAll(
                () -> assertThat(actualProduct.getId()).isNotNull(),
                () -> assertThat(actualProduct.getName()).isEqualTo(expectedProduct.getName()),
                () -> assertThat(actualProduct.getPrice()).isEqualTo(expectedProduct.getPrice()),
                () -> assertThat(actualProduct.getImageUrl()).isEqualTo(expectedProduct.getImageUrl()),
                () -> assertThat(actualProduct.getApproved()).isEqualTo(expectedProduct.getApproved()),
                () -> assertThat(actualProduct.getDescription()).isEqualTo(expectedProduct.getDescription())
        );
    }

    @Test
    @DisplayName("상품 레퍼지토리에 상품이 정상적으로 조회되는지 테스트")
    void find() {
        Product expectedProduct = productRepository.save(
                new Product(
                        "테스트",
                        10000L,
                        "https://test.com",
                        true,
                        ""
                ));

        Long id = productRepository.save(expectedProduct).getId();
        Product actualProduct = productRepository.findById(id).get();

        assertAll(
                () -> assertThat(actualProduct.getId()).isNotNull(),
                () -> assertThat(actualProduct.getName()).isEqualTo(expectedProduct.getName()),
                () -> assertThat(actualProduct.getPrice()).isEqualTo(expectedProduct.getPrice()),
                () -> assertThat(actualProduct.getImageUrl()).isEqualTo(expectedProduct.getImageUrl()),
                () -> assertThat(actualProduct.getApproved()).isEqualTo(expectedProduct.getApproved()),
                () -> assertThat(actualProduct.getDescription()).isEqualTo(expectedProduct.getDescription())
        );
    }

}