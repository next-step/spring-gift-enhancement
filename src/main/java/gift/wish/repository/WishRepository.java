package gift.wish.repository;

import gift.product.entity.Product;
import gift.wish.dto.WishResponseDto;
import gift.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
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
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish,Long> {

    List<Wish> findByMemberId(Long memberId);
    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);
    void deleteByMemberIdAndProductId(Long memberId, Long productId);

}
