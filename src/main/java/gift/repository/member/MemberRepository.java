package gift.repository.member;

import gift.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Member m set m.password = :newPassword where m.email = :email and m.password = :beforePassword")
    public int changePassword(
        @Param("email") String email,
        @Param("beforePassword") String beforePassword,
        @Param("newPassword") String afterPassword);

    public boolean existsByEmail(@Param("email") String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Member m set m.password = :newPassword where m.email = :email")
    void resetPassword(@Param("email") String email, @Param("newPassword") String newPassword);
}
