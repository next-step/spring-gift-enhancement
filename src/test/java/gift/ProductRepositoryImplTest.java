package gift;

import gift.domain.product.MdApprovalStatus;
import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(ProductRepositoryImpl.class)
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Test
    @DisplayName("상품 저장 및 조회 성공")
    void save_and_findById_success() {
        Product product = new Product("Test Product", 1000L, "http://image.url");
        productRepository.saveProduct(product);

        Product found = productRepository.findProductById(product.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Product");
        assertThat(found.getPrice()).isEqualTo(1000L);
        assertThat(found.getImageUrl()).isEqualTo("http://image.url");
        assertThat(found.isApproved()).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    void findById_notFound_throwsException() {
        assertThatThrownBy(() -> productRepository.findProductById(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found with id");
    }

    @Test
    @DisplayName("모든 상품 조회")
    void findAllProducts_returnsList() {
        Product p1 = new Product("Product 1", 1000L, "url1", MdApprovalStatus.approved());
        Product p2 = new Product("Product 2", 2000L, "url2", MdApprovalStatus.notApproved());
        productRepository.saveProduct(p1);
        productRepository.saveProduct(p2);

        List<Product> products = productRepository.findAllProducts();

        assertThat(products).hasSizeGreaterThanOrEqualTo(2);
        assertThat(products).extracting("name").contains("Product 1", "Product 2");
    }

    @Test
    @DisplayName("상품 정보 업데이트")
    void updateProduct_success() {
        Product product = new Product("Old Name", 500L, "oldUrl");
        productRepository.saveProduct(product);

        productRepository.updateProduct(product.getId(), "New Name", 1500L, "newUrl");
        Product updated = productRepository.findProductById(product.getId());

        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getPrice()).isEqualTo(1500L);
        assertThat(updated.getImageUrl()).isEqualTo("newUrl");
        assertThat(updated.isApproved()).isFalse();
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct_success() {
        Product product = new Product("To Delete", 123L, "url",MdApprovalStatus.notApproved() );
        productRepository.saveProduct(product);

        productRepository.deleteProduct(product.getId());

        assertThatThrownBy(() -> productRepository.findProductById(product.getId()))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품 삭제 시 예외 발생")
    void deleteProduct_notFound_throwsException() {
        assertThatThrownBy(() -> productRepository.deleteProduct(999L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("MD 승인 여부 조회")
    void findMdApprovedById_returnsCorrectValue() {
        Product approved = new Product("Approved", 1000L, "url", MdApprovalStatus.approved());
        Product notApproved = new Product("Not Approved", 1000L, "url", MdApprovalStatus.notApproved());
        productRepository.saveProduct(approved);
        productRepository.saveProduct(notApproved);

        assertThat(productRepository.findMdApprovedById(approved.getId())).isTrue();
        assertThat(productRepository.findMdApprovedById(notApproved.getId())).isFalse();
    }

    @Test
    @DisplayName("상품 존재 여부 확인")
    void existsById_returnsCorrectValue() {
        Product product = new Product("Exists", 500L, "url", MdApprovalStatus.notApproved());
        productRepository.saveProduct(product);

        assertThat(productRepository.existsById(product.getId())).isTrue();
        assertThat(productRepository.existsById(999L)).isFalse();
    }
}
