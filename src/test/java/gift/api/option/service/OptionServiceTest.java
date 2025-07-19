package gift.api.option.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import gift.api.option.domain.Option;
import gift.api.option.dto.OptionRequestDto;
import gift.api.option.dto.OptionResponseDto;
import gift.api.option.repository.OptionRepository;
import gift.api.product.domain.Product;
import gift.api.product.repository.ProductRepository;
import gift.exception.conflict.OptionNameDuplicateException;
import gift.exception.option.InvalidOptionAccessException;
import gift.exception.option.OptionPolicyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private Option option1;
    private Option option2;

    @BeforeEach
    void setUp() {
        // Product 객체 생성
        product = new Product("테스트 상품", 10000L, "image.jpg");
        // ReflectionTestUtils를 사용해 Product 객체에 ID를 설정합니다.
        ReflectionTestUtils.setField(product, "id", 1L);

        // Option 객체들 생성
        option1 = new Option("기본", 100, product);
        ReflectionTestUtils.setField(option1, "id", 100L);

        option2 = new Option("추가", 50, product);
        ReflectionTestUtils.setField(option2, "id", 101L);

        // Product에 Option 목록을 설정합니다.
        // 기존의 addOption 대신, 테스트에서 제어하기 쉽도록 List를 직접 설정합니다.
        List<Option> options = new ArrayList<>(List.of(option1, option2));
        ReflectionTestUtils.setField(product, "options", options);
    }

    @Test
    @DisplayName("옵션 추가 성공")
    void addOption_success() {
        // given
        OptionRequestDto requestDto = new OptionRequestDto("새 옵션", 10);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.findByProductAndName(product, "새 옵션")).willReturn(Optional.empty());
        given(optionRepository.save(any(Option.class))).willReturn(new Option("새 옵션", 10, product));

        // when
        OptionResponseDto response = optionService.addOption(1L, requestDto);

        // then
        assertThat(response.name()).isEqualTo("새 옵션");
        assertThat(response.quantity()).isEqualTo(10);
        verify(optionRepository).save(any(Option.class));
    }

    @Test
    @DisplayName("옵션 추가 실패 - 중복된 이름")
    void addOption_fail_duplicateName() {
        // given
        OptionRequestDto requestDto = new OptionRequestDto("기본", 10);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.findByProductAndName(product, "기본")).willReturn(
                Optional.of(option1));

        // when & then
        assertThatThrownBy(() -> optionService.addOption(1L, requestDto))
                .isInstanceOf(OptionNameDuplicateException.class)
                .hasMessage("이미 존재하는 옵션 이름입니다: 기본");
    }

    @Test
    @DisplayName("옵션 삭제 성공")
    void deleteOption_success() {
        // given
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.findById(101L)).willReturn(Optional.of(option2));

        // when
        optionService.deleteOption(1L, 101L);

        // then
        assertThat(product.getOptions()).hasSize(1).contains(option1);
    }

    @Test
    @DisplayName("옵션 삭제 실패 - 상품에 옵션이 하나뿐인 경우")
    void deleteOption_fail_lastOption() {
        // given
        product.getOptions().remove(option2); // 옵션을 하나만 남김
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.findById(100L)).willReturn(Optional.of(option1));

        // when & then
        assertThatThrownBy(() -> optionService.deleteOption(1L, 100L))
                .isInstanceOf(OptionPolicyException.class)
                .hasMessage("상품에는 최소 1개의 옵션이 존재해야 합니다.");
    }

    @Test
    @DisplayName("옵션 삭제 실패 - 다른 상품의 옵션을 삭제하려는 경우")
    void deleteOption_fail_invalidAccess() {
        // given
        // 1. 다른 ID(2L)를 가진 'otherProduct'를 생성합니다.
        Product otherProduct = new Product("다른 상품", 20000L, "other.jpg");
        ReflectionTestUtils.setField(otherProduct, "id", 2L);

        // 2. productRepository.findById(2L) 호출 시 otherProduct를 반환하도록 설정합니다.
        given(productRepository.findById(2L)).willReturn(Optional.of(otherProduct));
        // 3. optionRepository.findById(100L) 호출 시 @BeforeEach에서 생성된 option1을 반환합니다.
        //    이 option1은 ID가 1L인 'product'에 속해 있습니다.
        given(optionRepository.findById(100L)).willReturn(Optional.of(option1));

        // when & then
        // 4. '다른 상품(ID: 2L)'에서 '기존 상품(ID: 1L)'에 속한 '옵션(ID: 100L)'을 삭제하도록 요청합니다.
        //    이 호출은 예외를 발생시켜야 합니다.
        assertThatThrownBy(() -> optionService.deleteOption(2L, 100L))
                .isInstanceOf(InvalidOptionAccessException.class)
                .hasMessage("해당 상품에 속한 옵션이 아닙니다.");
    }
}