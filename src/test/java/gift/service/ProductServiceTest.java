package gift.service;

import gift.dto.ProductRequest;
import gift.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("존재하지 않는 상품 수정 시 예외 발생 테스트")
    void updateNonExistentProduct_ThrowsException() {
        Long nonExistentId = 999L; // 존재하지 않는 ID
        ProductRequest request = new ProductRequest("수정 시도", 5000, "update.jpg");

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(nonExistentId, request);
        });

        assertThat(exception.getMessage()).isEqualTo("해당 ID의 상품을 찾을 수 없습니다: " + nonExistentId);
    }
}
