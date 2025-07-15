package gift.wish.repository;

import gift.wish.dto.WishResponseDto;
import gift.wish.entity.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wish> wishRowMapper = (resultSet , rowNum) -> {
        Wish wish = new Wish(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id"),
                resultSet.getInt("quantity")
        );
        return wish;
    };

    public List<Wish> getWishList(Wish wish) {
        String sql = "select * from wish where member_id=?";
        return jdbcTemplate.query(sql, wishRowMapper, wish.getMemberId());
    }

    public Wish addWish(Wish wish) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into wish(member_id,product_id,quantity) values(?,?,?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
                PreparedStatement ps = connection.prepareStatement(sql , PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setLong(1, wish.getMemberId());
                ps.setLong(2, wish.getProductId());
                ps.setInt(3, wish.getQuantity());
                return ps;
            }
        },keyHolder);

        if(keyHolder.getKey() != null){
            wish.setId(keyHolder.getKey().longValue());
            return wish;
        }
        return null;
    }

    public void deleteWish(Wish wish) {
        String sql = "delete from wish where member_id=? and product_id=?";

        jdbcTemplate.update(sql, wish.getMemberId() , wish.getProductId());
    }

}
