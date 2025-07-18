package gift.member.service;

import gift.authorization.dto.TokenResponseDto;
import gift.authorization.exception.UnauthorizedException;
import gift.authorization.service.JwtProvider;
import gift.member.Member;
import gift.member.Role;
import gift.member.dto.MemberAddRequestDto;
import gift.member.dto.MemberLoginRequestDto;
import gift.member.dto.MemberRegisterRequestDto;
import gift.member.dto.MemberResponseDto;
import gift.member.dto.MemberUpdateRequestDto;
import gift.member.exception.InvalidMemberException;
import gift.member.exception.MemberNotFoundException;
import gift.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static gift.authorization.service.SaltedSHA256.hashWithSHA256;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberServiceImpl(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void addMember(MemberAddRequestDto requestDto) {
        validateUniqueEmail(requestDto.email(), "admin/memberAdd");
        validateMemberRole(requestDto.role(), "admin/memberAdd");
        String hashedPassword = hashWithSHA256(requestDto.password());
        Member member = new Member(requestDto.email(), hashedPassword, requestDto.name(), requestDto.role());
        memberRepository.save(member);
    }

    @Override
    public TokenResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateUniqueEmail(requestDto.email(), "admin/memberAdd");

        String hashedPassword = hashWithSHA256(requestDto.password());

        Member member = new Member(requestDto.email(), hashedPassword, requestDto.name(), "USER");
        Member savedMember = memberRepository.save(member);

        return new TokenResponseDto(jwtProvider.createToken(savedMember.getId(), savedMember.getName(), savedMember.getEmail(), savedMember.getRole()));
    }

    @Override
    public TokenResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = findMemberByEmailOrElseThrow(requestDto.email());

        String hashedPassword = hashWithSHA256(requestDto.password());

        if (!member.isPasswordCorrect(hashedPassword)) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        return new TokenResponseDto(jwtProvider.createToken(member.getId(), member.getName(), member.getEmail(), member.getRole()));
    }

    @Override
    public MemberResponseDto findMemberById(Long id) {
        Member member = findMemberByIdOrElseThrow(id);
        return new MemberResponseDto(member);
    }

    @Override
    public List<MemberResponseDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> responseDtos = members.stream().map(Member::toMemberResponseDto).toList();
        return responseDtos;
    }

    @Override
    @Transactional
    public void updateMemberById(Long id, MemberUpdateRequestDto requestDto) {
        validateMemberRole(requestDto.role(), "admin/memberEdit");
        validateUniqueEmail(id,requestDto.email(), "admin/memberEdit");
        Member member = findMemberByIdOrElseThrow(id);
        member.update(requestDto);
    }

    @Override
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    private void validateUniqueEmail(String email, String viewName) {
        boolean isNotUniqueEmail = memberRepository.existsByEmail(email);
        if (isNotUniqueEmail) {
            throw new InvalidMemberException("이미 존재하는 이메일입니다.",viewName,"emailErrorMessage");
        }
    }

    private void validateUniqueEmail(Long memberId, String email, String viewName) {
        boolean isNotUniqueEmail = memberRepository.existsByEmailAndIdNot(email, memberId);
        if (isNotUniqueEmail) {
            throw new InvalidMemberException("이미 존재하는 이메일입니다.",viewName,"emailErrorMessage");
        }
    }

    private void validateMemberRole(String role, String viewName) {
        if (!Role.containsIgnoreCase(role)){
            throw new InvalidMemberException("잘못된 등급입니다.", viewName, "roleErrorMessage");
        }
    }

    @Override
    public Member findMemberByIdOrElseThrow(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    public Member findMemberByEmailOrElseThrow(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException(email));
    }

    @Override
    public void existsByIdOrElseThrow(Long id) {
        boolean isMember = memberRepository.existsById(id);
        if (!isMember) {
            throw new MemberNotFoundException(id);
        }
    }

}
