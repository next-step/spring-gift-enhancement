package gift.member.repository;

import gift.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndIdNotIn(String email, Long memberId);
    Optional<Member> findByEmail(String email);
}
