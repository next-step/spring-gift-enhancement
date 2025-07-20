package gift.member.repository;

import gift.global.exception.MemberNotFoundException;
import gift.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    default Member getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    default Member getByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }
}
