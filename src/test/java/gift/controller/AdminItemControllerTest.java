package gift.controller;

import gift.Jwt.JwtUtil;
import gift.Jwt.TokenUtils;
import gift.dto.itemDto.ItemCreateDto;
import gift.entity.User;
import gift.entity.UserRole;
import gift.repository.itemRepository.ItemRepository;
import gift.service.itemService.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureWebTestClient
@Transactional
class AdminItemControllerTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void 관리자상품저장_성공() {
        ItemCreateDto dto = new ItemCreateDto("카카오", 1500, "juice.png", true);

        itemService.saveItem(dto);

        assertThat(itemRepository.findAll()).anyMatch(item -> item.getName().equals("카카오"));
    }

    @Test
    void 관리자가아닌경우상품저장_예외발생() {
        ItemCreateDto dto = new ItemCreateDto("카카오", 1500, "juice.png", false);

        assertThatThrownBy(() -> itemService.saveItem(dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 토큰에서이메일_추출성공() {
        User user = new User(1L, "tester@example.com", "securePassword", UserRole.USER);
        String token = jwtUtil.generateToken(user);

        String extractedEmail = tokenUtils.extractEmail(token);

        assertThat(extractedEmail).isEqualTo("tester@example.com");
    }
}
