package gift.repository;

import gift.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email.value = :email")
    Optional<Member> findByEmailValue(@Param("email") String email);

    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.email.value = :email")
    boolean existsByEmailValue(@Param("email") String email);
}
