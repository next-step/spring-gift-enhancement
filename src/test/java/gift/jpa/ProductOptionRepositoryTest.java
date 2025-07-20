package gift.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.entity.Product;
import gift.entity.ProductOption;
import gift.repository.product.ProductOptionRepository;
import gift.repository.product.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductOptionRepositoryTest {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 옵션 생성 성공")
    void save_success() {
        Product product = new Product("example4", 4700, 1, "https://www.starbucks.co.kr/index.do");
        productRepository.save(product);

        ProductOption expected = new ProductOption("option1", 1, product);
        ProductOption actual = productOptionRepository.save(expected);

        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity())
        );
    }

    @Test
    @DisplayName("상품 옵션 생성 실패1 - 0 이하 quantity 값")
    void save_fail1() {
        Product product = new Product("example4", 4700, 1, "https://www.starbucks.co.kr/index.do");
        productRepository.save(product);

        ProductOption expected = new ProductOption("option1", 0, product);

        assertThrows(ConstraintViolationException.class, () ->
            productOptionRepository.save(expected)
        );
    }

    @Test
    @DisplayName("상품 옵션 생성 실패2 - 1억 이상 quantity 값")
    void save_fail2() {
        Product product = new Product("example4", 4700, 100000000,
            "https://www.starbucks.co.kr/index.do");
        productRepository.save(product);

        ProductOption expected = new ProductOption("option1", 100000000, product);

        assertThrows(ConstraintViolationException.class, () -> {
            productOptionRepository.save(expected);
            productRepository.flush();
        });
    }

    @Test
    @DisplayName("상품 옵션 검색 성공")
    void findById() {
        Product product = new Product("example4", 4700, 1, "https://www.starbucks.co.kr/index.do");
        productRepository.save(product);

        ProductOption expected = new ProductOption("option1", 1, product);
        ProductOption actual = productOptionRepository.save(expected);

        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity())
        );
    }

    @Test
    @DisplayName("상품 옵션 업데이트 성공")
    void update() {
        Product product = new Product("example4", 4700, 1, "https://www.starbucks.co.kr/index.do");
        productRepository.save(product);

        ProductOption option = new ProductOption("option1", 1, product);
        productOptionRepository.save(option);

        ProductOption option2 = productOptionRepository.findById(4L).get();

        String expectedName = "changeOption";
        int expectedQuantity = 2;
        option2.change(expectedName, expectedQuantity);

        ProductOption actual = productOptionRepository.findById(4L).get();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expectedName),
            () -> assertThat(actual.getQuantity()).isEqualTo(expectedQuantity)
        );
    }

    @Test
    @DisplayName("상품 옵션 삭제 성공")
    void delete() {
        Product product = new Product("example4", 4700, 1, "https://www.starbucks.co.kr/index.do");
        productRepository.save(product);

        ProductOption option = new ProductOption("option1", 1, product);
        productOptionRepository.save(option);

        int deleteRow = productOptionRepository.deleteProductOptionById(4L);
        assertThat(deleteRow).isEqualTo(1);
    }
}
