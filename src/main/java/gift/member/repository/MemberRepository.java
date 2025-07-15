package gift.member.repository;

import gift.member.domain.Member;
import gift.member.domain.RoleType;
import java.util.Optional;

public interface MemberRepository {

    Member save(String email, String password, RoleType role);

    Optional<Member> findByEmail(String email);

    void updatePassword(Long id, String newPassword);

    void deleteById(Long id);
}
