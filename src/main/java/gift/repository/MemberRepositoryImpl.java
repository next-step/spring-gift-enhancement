package gift.repository;

import gift.domain.Member;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(Member member) {
        Objects.requireNonNull(member, "member는 null일 수 없습니다.");

        try {
            return memberJpaRepository.save(member);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(ErrorCode.USER_EMAIL_ALREADY_EXIST);
        }
    }

    @Override
    public void update(Long id, Member updatedMember) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");
        Objects.requireNonNull(updatedMember, "updatedMember는 null일 수 없습니다.");

        Member existingMember = memberJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원이 존재하지 않아 업데이트할 수 없습니다: " + id));

        existingMember.changeEmail(updatedMember.email());
        existingMember.changePassword(updatedMember.password());
        
        memberJpaRepository.save(existingMember);
    }

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return memberJpaRepository.findByEmail(email);
    }
}
