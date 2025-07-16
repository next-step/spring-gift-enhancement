package gift.member.repository;

import gift.member.domain.Member;
import gift.member.domain.RoleType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Primary
public class MemberJpaRepositoryAdapter implements MemberRepository {
    private final MemberJpaRepository repository;
    public MemberJpaRepositoryAdapter(MemberJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member save(String email, String password, RoleType role) {
        return repository.save(new Member(email, password, role));
    }
    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String newPassword) {
        repository.findById(id).ifPresent(member -> member.updatePassword(newPassword));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
