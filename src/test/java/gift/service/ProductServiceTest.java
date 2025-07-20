package gift.service;

import gift.common.exception.ProductNotFoundException;
import gift.domain.Product;
import gift.dto.product.CreateProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    Product product;

    @BeforeEach
    void before() {
        CreateProductRequest createProductRequest = new CreateProductRequest("칫솔", "image", 10000, 12);
        product = productService.saveProduct(createProductRequest);
    }

    @Test
    @DisplayName("사용자는 상품을 저장할 수 있다.")
    void test1() {
        CreateProductRequest createProductRequest = new CreateProductRequest("칫솔", "image", 10000, 12);

        Product product = productService.saveProduct(createProductRequest);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo("칫솔");
        assertThat(product.getImageUrl()).isEqualTo("image");
        assertThat(product.getPrice()).isEqualTo(10000);
        assertThat(product.getQuantity()).isEqualTo(12);

    }

    @Test
    @DisplayName("사용자는 상품을 수정할 수 있다.")
    void test2() {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest("칫솔2", "image2", 30000, 111);
        Product update = productService.updateProduct(product.getId(), updateProductRequest);

        assertThat(update.getId()).isEqualTo(product.getId());
        assertThat(update.getName()).isEqualTo("칫솔2");
        assertThat(update.getImageUrl()).isEqualTo("image2");
        assertThat(update.getPrice()).isEqualTo(30000);
        assertThat(update.getQuantity()).isEqualTo(111);
    }

    @Test
    @DisplayName("사용자는 상품 목록을 조회할 수 있다.")
    void test3() {
        CreateProductRequest createProductRequest = new CreateProductRequest("칫솔", "image", 10000, 12);
        productService.saveProduct(createProductRequest);

        //beforeEach에서 생성한 것 까지 총 2건의 데이터 있음
        List<ProductResponse> products = productService.getAllProducts(null, PageRequest.of(1, 10));
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자는 상품 단건을 조회할 수 있다.")
    void test4() {
        Product getProduct = productService.getProduct(this.product.getId());

        assertThat(getProduct.getId()).isEqualTo(product.getId());
        assertThat(getProduct.getName()).isEqualTo("칫솔");
        assertThat(getProduct.getImageUrl()).isEqualTo("image");
        assertThat(getProduct.getPrice()).isEqualTo(10000);
        assertThat(getProduct.getQuantity()).isEqualTo(12);
    }

    @Test
    @DisplayName("사용자는 상품을 삭제할 수 있다.")
    void test5() {
        productService.deleteProduct(product.getId());

        assertThatThrownBy(() -> productService.getProduct(product.getId())).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("getProducts() 메서드 페이지네이션 테스트 1 - cursor로 페이지를 구분하기 때문에 page 값이 들어오더라도 cursor 값에 의해서만 페이지가 변경되어야 한다.")
    void test6_1() {
        productService.saveProduct(new CreateProductRequest("칫솔1", "image", 10000, 12));
        Product product2 = productService.saveProduct(new CreateProductRequest("칫솔2", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔3", "image", 10000, 12));

        List<ProductResponse> products1 = productService.getAllProducts(product2.getId(), PageRequest.of(2, 3, Sort.by("id").descending())); //pageNumber = 2
        List<ProductResponse> products2 = productService.getAllProducts(product2.getId(), PageRequest.of(4, 3, Sort.by("id").descending())); //pageNumber = 4

        //product1과 product2의 결과는 같아야 함
        assertThat(products1.size()).isEqualTo(products2.size());
        assertThat(products1).isEqualTo(products2);
    }

    @Test
    @DisplayName("getProducts() 메서드 페이지네이션 테스트 2 - 커서를 기준으로 다음 데이터를 불러올 수 있다.")
    void test6_2() {
        Product product1 = productService.saveProduct(new CreateProductRequest("칫솔1", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔2", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔3", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔4", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔5", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔6", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔7", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔8", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔9", "image", 10000, 12));
        productService.saveProduct(new CreateProductRequest("칫솔10", "image", 10000, 12));

        List<ProductResponse> products = productService.getAllProducts(product1.getId(), PageRequest.of(0, 10, Sort.by("id").descending()));

        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).name()).isEqualTo("칫솔");
    }
}