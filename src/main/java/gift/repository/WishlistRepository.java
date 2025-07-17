package gift.repository;

import gift.domain.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByProductId(Long productId);

    @Query("select w from Wishlist w where w.user.id=:userId order by w.id desc")
    Page<Wishlist> findAllByUserId(Long userId, PageRequest pageRequest);
}
