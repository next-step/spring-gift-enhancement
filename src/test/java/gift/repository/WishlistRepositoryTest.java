package gift.repository;

import gift.domain.Product;
import gift.domain.Role;
import gift.domain.User;
import gift.domain.Wishlist;
import gift.dto.wishlist.WishlistResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    User user;
    Product product;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("tkddnr@tkddnr.com", "1234", Role.USER));
        product = productRepository.save(new Product("감자칩", "image", 10000, 100));
    }

    @Test
    @DisplayName("위시리스트 저장")
    void test1() {
        Wishlist wishlist = new Wishlist(user, product);
        Wishlist save = wishlistRepository.save(wishlist);

        assertThat(save.getId()).isNotNull();
        assertThat(save.getUser().getId()).isEqualTo(user.getId());
        assertThat(save.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("위시리스트 조회")
    void test2() {
        Wishlist wishlist = new Wishlist(user, product);
        Wishlist save = wishlistRepository.save(wishlist);

        em.flush();
        em.clear();

        Wishlist getWishlist = wishlistRepository.findById(save.getId()).get();

        assertThat(getWishlist.getId()).isNotNull();
        assertThat(getWishlist.getProduct().getId()).isEqualTo(save.getProduct().getId());
        assertThat(getWishlist.getUser().getId()).isEqualTo(save.getUser().getId());
    }

    @Test
    @DisplayName("위시리스트 삭제")
    void test3() {
        Wishlist wishlist = new Wishlist(user, product);
        Wishlist save = wishlistRepository.save(wishlist);

        em.flush();
        em.clear();

        wishlistRepository.delete(wishlist);

        em.flush();

        Optional<Wishlist> getWishlist = wishlistRepository.findById(save.getId());

        assertThat(getWishlist).isEmpty();
    }

    @Test
    @DisplayName("findByProductId 테스트")
    void test4() {
        Wishlist wishlist = new Wishlist(user, product);
        wishlistRepository.save(wishlist);

        em.flush();

        Optional<Wishlist> byProductId = wishlistRepository.findByProductId(product.getId());

        assertThat(byProductId).isNotEmpty();

        Wishlist getWishlist = byProductId.get();

        assertThat(getWishlist.getId()).isNotNull();
        assertThat(getWishlist.getProduct().getId()).isEqualTo(product.getId());
        assertThat(getWishlist.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("findAllByUserId 테스트")
    void test5() {
        Wishlist wishlist = new Wishlist(user, product);
        wishlistRepository.save(wishlist);

        em.flush();

        List<WishlistResponse> responses = wishlistRepository.findAllByUserId(user.getId());

        assertThat(responses.size()).isEqualTo(1);

        WishlistResponse response = responses.get(0);

        assertThat(response.getProductId()).isEqualTo(product.getId());
        assertThat(response.getProductName()).isEqualTo(product.getName());
        assertThat(response.getProductPrice()).isEqualTo(product.getPrice());
        assertThat(response.getProductImageUrl()).isEqualTo(product.getImageUrl());
    }
}
