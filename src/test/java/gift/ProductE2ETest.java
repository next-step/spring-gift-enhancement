package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import gift.api.member.domain.MemberRole;
import gift.api.member.dto.MemberRequestDto;
import gift.api.member.dto.TokenResponseDto;
import gift.api.product.dto.ProductRequestDto;
import gift.api.product.dto.ProductResponseDto;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductE2ETest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @Autowired
    private JdbcClient jdbcClient;

    private String authToken;

    @BeforeEach
    void setUp() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        String email = "test@test.com";
        String password = "password";
        jdbcClient.sql(
                        "INSERT INTO member(email, password, role) VALUES (:email, :password, :role)")
                .param("email", email)
                .param("password", BCrypt.hashpw(password, BCrypt.gensalt()))
                .param("role", MemberRole.USER.name())
                .update();

        TokenResponseDto tokenResponse = restClient.post()
                .uri("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MemberRequestDto(email, password))
                .retrieve()
                .body(TokenResponseDto.class);

        this.authToken = Objects.requireNonNull(tokenResponse).token();
    }

    @AfterEach
    void tearDown() {
        jdbcClient.sql("delete from product").update();
        jdbcClient.sql("delete from member").update();
    }

    @Test
    void 상품_전체_조회_테스트() {
        List<?> productList = restClient.get()
                .uri("/api/products")
                .header("Authorization", authToken)
                .retrieve()
                .body(List.class);

        assertThat(productList).isNotNull();
    }

    @Test
    void 특정_상품_조회_테스트() {
        ProductRequestDto request = new ProductRequestDto(
                "Product 2",
                500L,
                "https://image.com/2.jpg"
        );

        ProductResponseDto created = restClient.post()
                .uri("/api/products")
                .header("Authorization", authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ProductResponseDto.class);

        ProductResponseDto found = restClient.get()
                .uri("/api/products/" + created.id())
                .header("Authorization", authToken)
                .retrieve()
                .body(ProductResponseDto.class);

        assertThat(found.id()).isEqualTo(created.id());
        assertThat(found.name()).isEqualTo("Product 2");
    }

    @Test
    void 상품_추가_테스트() {
        ProductRequestDto request = new ProductRequestDto(
                "Product 1",
                100L,
                "https://image.com/1.jpg"
        );

        ProductResponseDto response = restClient.post()
                .uri("/api/products")
                .header("Authorization", authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ProductResponseDto.class);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("Product 1");
        assertThat(response.price()).isEqualTo(100L);
        assertThat(response.imageUrl()).isEqualTo("https://image.com/1.jpg");
    }

    @Test
    void 상품_수정_테스트() {
        ProductRequestDto request = new ProductRequestDto(
                "Product 3",
                200L,
                "https://image.com/3.jpg"
        );

        ProductResponseDto created = restClient.post()
                .uri("/api/products")
                .header("Authorization", authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ProductResponseDto.class);

        ProductRequestDto updated = new ProductRequestDto("UpdatedProd3", 999L,
                "https://updated.com/3.jpg");

        ProductResponseDto result = restClient.put()
                .uri("/api/products/" + created.id())
                .header("Authorization", authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updated)
                .retrieve()
                .body(ProductResponseDto.class);

        assertThat(result.name()).isEqualTo("UpdatedProd3");
        assertThat(result.price()).isEqualTo(999L);
    }

    @Test
    void 상품_삭제_테스트() {
        ProductRequestDto request = new ProductRequestDto(
                "Product 4",
                300L,
                "https://image.com/4.jpg"
        );

        ProductResponseDto created = restClient.post().uri("/api/products")
                .header("Authorization", authToken).contentType(MediaType.APPLICATION_JSON)
                .body(request).retrieve().body(ProductResponseDto.class);

        restClient.delete().uri("/api/products/" + created.id())
                .header("Authorization", authToken).retrieve().toBodilessEntity();

        Throwable thrown = catchThrowable(() -> {
            restClient.get().uri("/api/products/" + created.id())
                    .header("Authorization", authToken).retrieve().body(ProductResponseDto.class);
        });

        assertThat(thrown)
                .isInstanceOf(HttpClientErrorException.class)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND);
    }

    @Test
    void 상품_이름_최대_15자_실패() {
        ProductRequestDto request = new ProductRequestDto(
                "이름이너무길어서검증에걸리는상품",
                100L,
                "https://image.com/longname.jpg"
        );

        Throwable thrown = catchThrowable(() -> {
            restClient.post().uri("/api/products")
                    .header("Authorization", authToken)
                    .contentType(MediaType.APPLICATION_JSON).body(request)
                    .retrieve().body(ProductResponseDto.class);
        });

        assertThat(thrown)
                .isInstanceOf(HttpClientErrorException.class)
                .satisfies(e -> {
                    HttpClientErrorException ex = (HttpClientErrorException) e;
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                    assertThat(ex.getResponseBodyAsString()).contains("상품 이름은 최대 15자여야 합니다.");
                });
    }

    @Test
    void 상품_이름_특수_문자_성공() {
        ProductRequestDto request = new ProductRequestDto(
                "()[]+-&/_ 이건 됨",
                100L,
                "https://image.com/success.jpg"
        );

        ProductResponseDto response = restClient.post()
                .uri("/api/products")
                .header("Authorization", authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ProductResponseDto.class);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("()[]+-&/_ 이건 됨");
    }

    @Test
    void 상품_이름_특수_문자_실패() {
        ProductRequestDto request = new ProductRequestDto(
                "!이건 안됨!",
                100L,
                "https://image.com/bad.jpg"
        );

        Throwable thrown = catchThrowable(
                () -> restClient.post().uri("/api/products").header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON).body(request).retrieve()
                        .body(ProductResponseDto.class));

        assertThat(thrown).isInstanceOf(HttpClientErrorException.class)
                .satisfies(e -> {
                    var ex = (HttpClientErrorException) e;
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                    assertThat(ex.getResponseBodyAsString()).contains(
                            "상품 이름에는 (), [], +, -, &, /, _ 외의 특수 문자를 사용할 수 없습니다.");
                });
    }

    @Test
    void 상품_이름_MD_승인_글자() {
        ProductRequestDto request = new ProductRequestDto(
                "카카오커피",
                100L,
                "https://image.com/bad.jpg"
        );

        Throwable thrown = catchThrowable(
                () -> restClient.post().uri("/api/products").header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON).body(request).retrieve()
                        .body(ProductResponseDto.class));

        assertThat(thrown).isInstanceOf(HttpClientErrorException.class)
                .satisfies(e -> {
                    var ex = (HttpClientErrorException) e;
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                    assertThat(ex.getResponseBodyAsString()).contains(
                            "담당 MD의 승인이 필요한 단어가 포함되어 있습니다");
                });
    }

    @Test
    void 상품_가격_0원_이상_실패() {
        ProductRequestDto request = new ProductRequestDto(
                "Invalid Price",
                -100L,
                "https://image.com/invalid.jpg"
        );

        Throwable thrown = catchThrowable(
                () -> restClient.post().uri("/api/products").header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON).body(request).retrieve()
                        .body(ProductResponseDto.class));

        assertThat(thrown).isInstanceOf(HttpClientErrorException.class)
                .satisfies(e -> {
                    var ex = (HttpClientErrorException) e;
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                    assertThat(ex.getResponseBodyAsString()).contains("가격은 0 이상이어야 합니다.");
                });
    }
}