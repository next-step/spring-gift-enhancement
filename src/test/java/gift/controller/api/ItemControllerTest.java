package gift.controller.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.ItemRequest;
import gift.dto.LoginResponse;
import gift.dto.MemberLoginRequest;
import gift.entity.Item;
import gift.repository.ItemRepository;
import gift.service.MemberService;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemRepository itemRepository;

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        LoginResponse adminLogin = memberService.login(new MemberLoginRequest("admin@example.com", "admin1234"));
        adminToken = adminLogin.token();
        LoginResponse userLogin = memberService.login(new MemberLoginRequest("user@example.com", "user1234"));
        userToken = userLogin.token();
    }

    @Test
    @DisplayName("API - 전체 상품 목록 조회 (페이지네이션 적용)")
    void getAllItems() throws Exception {
        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.totalElements").value(15));
    }

    @Test
    @DisplayName("ADMIN 권한으로 상품 등록 성공")
    void createItem_By_Admin_Succeeds() throws Exception {
        ItemRequest request = new ItemRequest("관리자 등록 상품", 5000, "admin_item.jpg");

        mockMvc.perform(post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("API - 상품 수정 성공 (ADMIN)")
    void updateItem_Success_By_Admin() throws Exception {
        Item itemToUpdate = itemRepository.save(new Item(null, "수정 전 상품", 1000, "before.jpg"));
        ItemRequest itemRequest = new ItemRequest("수정된 상품", 1500, "after.jpg");
        String requestBody = objectMapper.writeValueAsString(itemRequest);

        mockMvc.perform(put("/api/products/" + itemToUpdate.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("수정된 상품"));
    }

    @Test
    @DisplayName("API - 상품 삭제 성공 (ADMIN)")
    void deleteItem_Success_By_Admin() throws Exception {
        Item itemToDelete = itemRepository.save(new Item(null, "삭제될 상품", 1000, "delete.jpg"));

        mockMvc.perform(delete("/api/products/" + itemToDelete.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("인증 토큰 없이 상품 등록 API 호출 시 401 에러 발생")
    void createItem_Without_Token_Fails() throws Exception {
        ItemRequest request = new ItemRequest("인증 없는 상품", 100, "no-auth.jpg");

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized());
    }
}