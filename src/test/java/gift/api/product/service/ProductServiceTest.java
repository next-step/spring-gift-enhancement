package gift.api.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.api.option.repository.OptionRepository;
import gift.api.product.domain.Product;
import gift.api.product.dto.ProductRequestDto;
import gift.api.product.dto.ProductResponseDto;
import gift.api.product.repository.ProductRepository;
import gift.exception.notfound.ProductNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OptionRepository optionRepository;

    @Test
    @DisplayName("상품 생성 성공 - 기본 옵션과 함께")
    void createProduct_success() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto("새 상품", 15000L, "new.jpg");
        Product product = new Product(requestDto.name(), requestDto.price(), requestDto.imageUrl());
        ReflectionTestUtils.setField(product, "id", 1L); // ID 설정

        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        ProductResponseDto response = productService.createProduct(requestDto);

        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("새 상품");
        // 기본 옵션이 저장되는지 확인
        verify(optionRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("상품 ID로 조회 성공")
    void findProductById_success() {
        // given
        Product product = new Product("테스트 상품", 10000L, "test.jpg");
        ReflectionTestUtils.setField(product, "id", 1L);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        ProductResponseDto response = productService.findProductById(1L);

        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("테스트 상품");
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 조회 실패")
    void findProductById_fail_notFound() {
        // given
        given(productRepository.findById(99L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.findProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("해당 ID의 상품을 찾을 수 없습니다: 99");
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct_success() {
        // given
        Product product = new Product("원본 상품", 10000L, "original.jpg");
        ReflectionTestUtils.setField(product, "id", 1L);
        ProductRequestDto requestDto = new ProductRequestDto("수정된 상품", 12000L, "updated.jpg");

        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        ProductResponseDto response = productService.updateProduct(1L, requestDto);

        // then
        assertThat(response.name()).isEqualTo("수정된 상품");
        assertThat(response.price()).isEqualTo(12000L);
        assertThat(product.getName()).isEqualTo("수정된 상품"); // 원본 객체가 변경되었는지 확인
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct_success() {
        // given
        given(productRepository.existsById(1L)).willReturn(true);

        // when
        productService.deleteProduct(1L);

        // then
        // deleteById가 1L을 인자로 호출되었는지 검증
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 상품 삭제 실패")
    void deleteProduct_fail_notFound() {
        // given
        given(productRepository.existsById(99L)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}