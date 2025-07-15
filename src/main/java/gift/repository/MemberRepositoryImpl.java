package gift.repository;

import gift.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository jpaRepository;

    public MemberRepositoryImpl(MemberJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Member saveMember(Member member) {
        return jpaRepository.save(member);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmailValue(email);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaRepository.findByEmailValue(email);
    }
}
