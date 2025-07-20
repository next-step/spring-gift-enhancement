package gift.service;

import gift.domain.User;
import gift.dto.product.CreateProductRequest;
import gift.dto.user.CreateUserRequest;
import gift.dto.wishlist.CreateWishlistRequest;
import gift.dto.wishlist.WishlistResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class WishlistServiceTest {

    @Autowired
    WishlistService wishlistService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    User user;

    @BeforeEach
    void setUp() {
        user = userService.saveUser(new CreateUserRequest("tkddnr@thanks.com", "1234"));
    }

    @Test
    @DisplayName("위시리스트 페이지네이션 테스트1 - 요청에 대한 올바른 데이터를 받아올 수 있다.")
    void test1() {
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필1", "image1", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필2", "image2", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필3", "image3", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필4", "image4", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필5", "image5", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필6", "image6", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필7", "image7", 10000, 100)).getId()));

        Page<WishlistResponse> response1 = wishlistService.getWishlistsByUserId(user.getId(), PageRequest.of(1, 3, Sort.by("id").descending()));
        Page<WishlistResponse> response2 = wishlistService.getWishlistsByUserId(user.getId(), PageRequest.of(2, 3, Sort.by("id").descending()));
        Page<WishlistResponse> response3 = wishlistService.getWishlistsByUserId(user.getId(), PageRequest.of(3, 3, Sort.by("id").descending()));

        assertThat(response1.getNumberOfElements()).isEqualTo(3);
        assertThat(response2.getNumberOfElements()).isEqualTo(3);
        assertThat(response3.getNumberOfElements()).isEqualTo(1);

        assertThat(response1.getContent().get(0).getProductName()).isEqualTo("연필7");
        assertThat(response1.getContent().get(2).getProductName()).isEqualTo("연필5");

        assertThat(response2.getContent().get(0).getProductName()).isEqualTo("연필4");
        assertThat(response2.getContent().get(2).getProductName()).isEqualTo("연필2");

        assertThat(response3.getContent().get(0).getProductName()).isEqualTo("연필1");
    }

    @Test
    @DisplayName("위시리스트 페이지네이션 테스트2 - 페이지 번호가 0으로 들어오더라도 첫 페이지를 보여준다. (사용자 입장에서 페이지 번호는 1부터 시작)")
    void test2() {
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필1", "image1", 10000, 100)).getId()));
        wishlistService.saveWishlist(user.getId(), new CreateWishlistRequest(productService.saveProduct(new CreateProductRequest("연필2", "image2", 10000, 100)).getId()));

        Page<WishlistResponse> response = wishlistService.getWishlistsByUserId(user.getId(), PageRequest.of(0, 3, Sort.by("id").descending()));

        assertThat(response.getNumberOfElements()).isEqualTo(2);

        assertThat(response.getContent().get(0).getProductName()).isEqualTo("연필2");
        assertThat(response.getContent().get(1).getProductName()).isEqualTo("연필1");
    }
}
