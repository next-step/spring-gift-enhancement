package gift;

import gift.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void 새로운_위시리스트_등록(){

    }

    @Test
    void 기존_위시리스트_수량추가(){

    }

    @Test
    void 위시리스트조회(){

    }

    @Test
    void 위시리스트삭제(){

    }
}