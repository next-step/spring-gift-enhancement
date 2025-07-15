package gift.wishlist.repository;

import gift.entity.WishlistItem;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {

    private final JdbcClient jdbcClient;

    public WishlistRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public int addWishlistItem(WishlistItem item) {
        int result = jdbcClient.sql("insert into wishlist (member_id, product_id, quantity) values (:memberId, :productId, :quantity)")
                .param("memberId", item.memberId())
                .param("productId", item.productId())
                .param("quantity", item.quantity())
                .update();
        return result;
    }

    @Override
    public Optional<WishlistItem> findWishlistById(Long id) {
        Optional<WishlistItem> item = jdbcClient.sql("select id, member_id, product_id, quantity from wishlist where id = :id")
                .param("id", id)
                .query(WishlistItem.class)
                .optional();
        return item;
    }

    @Override
    public List<WishlistItem> findAllWishlistItemsByMemberId(Long memberId) {
        List<WishlistItem> items = jdbcClient.sql("select id, member_id, product_id, quantity from wishlist where member_id = :memberId")
                .param("memberId", memberId)
                .query(WishlistItem.class)
                .list();
        return items;
    }

    @Override
    public int updateWishlistItemById(Long id, Long quantity) {
        int result = jdbcClient.sql("update wishlist set quantity = :quantity where id = :id")
                .param("quantity", quantity)
                .param("id", id)
                .update();
        return result;
    }

    @Override
    public int deleteById(Long id) {
        int result = jdbcClient.sql("delete from wishlist where id = :id")
                .param("id", id)
                .update();
        return result;
    }

    @Override
    public Optional<WishlistItem> findProductInMemberById(Long memberId, Long productId) {
        Optional<WishlistItem> result = jdbcClient.sql("select * from wishlist where member_id = :memberId and product_id = :productId")
                .param("memberId", memberId)
                .param("productId", productId)
                .query(WishlistItem.class)
                .optional();
        return result;
    }

}
