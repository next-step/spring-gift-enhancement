package gift.controller.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import gift.dto.MemberLoginRequest;
import gift.entity.Item;
import gift.repository.ItemRepository;
import gift.service.MemberService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberService memberService;

    private Item testItem;
    private Cookie adminCookie;

    @BeforeEach
    void setUp() {
        String token = memberService.login(new MemberLoginRequest("admin@example.com", "admin1234")).token();
        adminCookie = new Cookie("jwt-token", token);
        adminCookie.setPath("/");
        testItem = itemRepository.save(new Item(null, "사전 등록 상품", 20000, "before.jpg"));
    }

    @Test
    @DisplayName("관리자 페이지 - 상품 등록 성공")
    void createItem_Success() throws Exception {
        mockMvc.perform(post("/admin/items")
                .cookie(adminCookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "테스트 성공 상품")
                .param("price", "12000")
                .param("imageUrl", "success.jpg"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/items"));
    }

    @Test
    @DisplayName("관리자 페이지 - 상품 수정 성공")
    void updateItem_Success() throws Exception {
        mockMvc.perform(post("/admin/items/" + testItem.getId() + "/update")
                .cookie(adminCookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "수정된 상품")
                .param("price", "9999")
                .param("imageUrl", "updated.jpg"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/items/" + testItem.getId()));
    }

    @Test
    @DisplayName("관리자 페이지 - 상품 삭제 성공")
    void deleteItem_Success() throws Exception {
        mockMvc.perform(post("/admin/items/" + testItem.getId() + "/delete")
                .cookie(adminCookie))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/items"));
    }
}