package gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.Product;
import gift.dto.product.CreateProductRequest;
import gift.dto.user.CreateUserRequest;
import gift.dto.user.LoginRequest;
import gift.service.ProductService;
import gift.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductApiControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    String accessToken;

    @BeforeEach
    void setUp() {
        userService.saveUser(new CreateUserRequest("tkddnr@thanks.com", "1234"));
        accessToken = "Bearer " + userService.login(new LoginRequest("tkddnr@thanks.com", "1234")).accessToken();
    }

    @Test
    @DisplayName("상품 생성을 할 수 있다.")
    void test1() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("샤프", "image", 10000, 100)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("로그인 없이 상품 목록 조회를 할 수 있다.")
    void test2() throws Exception {
        //2개의 더미 데이터 생성
        productService.saveProduct(new CreateProductRequest("연필", "image", 1000, 10));
        productService.saveProduct(new CreateProductRequest("가방", "image", 2000, 100));

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("가방"))
                .andExpect(jsonPath("$[0].price").value(2000))
                .andExpect(jsonPath("$[1].name").value("연필"))
                .andExpect(jsonPath("$[1].price").value(1000));
    }

    @Test
    @DisplayName("상품 수정을 할 수 있다.")
    void test3() throws Exception {
        Product product = productService.saveProduct(new CreateProductRequest("연필", "image", 1000, 10));
        Long id = product.getId();

        String body = mapper.writeValueAsString(
                new CreateProductRequest("샤프", "image", 10000, 100)
        );

        mvc.perform(put("/api/products/" + id)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("상품 삭제를 할 수 있다.")
    void test4() throws Exception {
        Product product = productService.saveProduct(new CreateProductRequest("연필", "image", 1000, 10));
        Long id = product.getId();

        mvc.perform(delete("/api/products/" + id)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증1 - 상품 이름에 허용되지 않은 특수문자를 사용할 경우 상태코드 400를 반환한다.")
    void test5_1() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("??", "image", 10000, 100)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증2 - 상품 이름에 허용된 특수문자를 사용할 경우 상품을 생성할 수 있다.")
    void test5_2() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("[]()+-&/_", "image", 10000, 100)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증3 - 상품 이름에 [카카오] 라는 단어가 포함된 경우 상태코드 403을 반환한다.")
    void test5_3() throws Exception{
        String body = mapper.writeValueAsString(
                new CreateProductRequest("카카오톡", "image", 1000000, 1)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증4 - 상품 이름이 공백을 포함하여 15자리가 넘어 갈 경우 상태코드 400을 반환한다.")
    void test5_4() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("진짜 진짜 맛있는 고구마 칩[한정판]", "image", 10000, 100) // 16자리
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증5 - 상품 이름이 문자 없이 공백만 있을 경우 상태코드 400을 반환한다.")
    void test5_5() throws Exception{
        String body1 = mapper.writeValueAsString(
                new CreateProductRequest("", "image", 10000, 100)
        );

        String body2 = mapper.writeValueAsString(
                new CreateProductRequest(" ", "image", 10000, 100)
        );

        mvc.perform(post("/api/products")
                .content(body1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());

        mvc.perform(post("/api/products")
                .content(body2)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증6 - 상품 가격이 음수로 들어올 경우 상태코드 400을 반환한다.")
    void test5_6() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("맛동산", "image", -1, 100)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증7 - 상품 가격이 1억을 넘어갈 경우 상태코드 400을 반환한다.")
    void test5_7() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("맛동산", "image", 100_000_001, 100)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증8 - 상품 수량이 음수로 들어올 경우 상태코드 400을 반환한다.")
    void test5_8() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("맛동산", "image", 1000, -1)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증9 - 상품 수량이 1억을 넘어갈 경우 상태코드 400을 반환한다.")
    void test5_9() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("맛동산", "image", 1000, 100_000_001)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증10 - 상품 이미지 값이 들어오지 않을 경우 상태코드 400을 반환한다.")
    void test5_10() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("맛동산", null, 1000, 10000)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 dto 유효성 검증11 - 상품 이미지 값이 빈 문자열로 들어올 경우 상태코드 400을 반환한다.")
    void test5_11() throws Exception {
        String body = mapper.writeValueAsString(
                new CreateProductRequest("맛동산", "", 1000, 10000)
        );

        mvc.perform(post("/api/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("없는 아이디의 상품 수정을 요청할 경우 상태코드 400을 반환한다.")
    void test6() throws Exception{
        String body = mapper.writeValueAsString(
                new CreateProductRequest("샤프", "image", 10000, 100)
        );

        mvc.perform(put("/api/products/" + 11)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("없는 아이디의 상품 삭제를 요청할 경우 상태코드 400을 반환한다.")
    void test7() throws Exception {
        mvc.perform(delete("/api/products/" + 333)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        ).andExpect(status().isBadRequest());
    }

}