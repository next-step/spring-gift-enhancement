package gift.member.service;

import gift.authorization.dto.TokenResponseDto;
import gift.entity.Member;
import gift.member.dto.MemberAddRequestDto;
import gift.member.dto.MemberLoginRequestDto;
import gift.member.dto.MemberRegisterRequestDto;
import gift.member.dto.MemberResponseDto;
import gift.member.dto.MemberUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    public void addMember(MemberAddRequestDto requestDto);

    public TokenResponseDto registerMember(MemberRegisterRequestDto requestDto);

    public TokenResponseDto loginMember(MemberLoginRequestDto requestDto);

    public MemberResponseDto findMemberById(Long id);

    public List<MemberResponseDto> findAllMembers();

    public void updateMemberById(Long id, MemberUpdateRequestDto requestDto);

    public void deleteMemberById(Long id);

    Member findMemberByIdOrElseThrow(Long id);

    Member findMemberByEmailOrElseThrow(String email);
}
