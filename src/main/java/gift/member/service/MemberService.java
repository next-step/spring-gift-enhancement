package gift.member.service;

import gift.authorization.dto.TokenResponseDto;
import gift.member.Member;
import gift.member.dto.MemberAddRequestDto;
import gift.member.dto.MemberLoginRequestDto;
import gift.member.dto.MemberRegisterRequestDto;
import gift.member.dto.MemberResponseDto;
import gift.member.dto.MemberUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    void addMember(MemberAddRequestDto requestDto);

    TokenResponseDto registerMember(MemberRegisterRequestDto requestDto);

    TokenResponseDto loginMember(MemberLoginRequestDto requestDto);

    MemberResponseDto findMemberById(Long id);

    List<MemberResponseDto> findAllMembers();

    void updateMemberById(Long id, MemberUpdateRequestDto requestDto);

    void deleteMemberById(Long id);

    Member findMemberByIdOrElseThrow(Long id);

    Member findMemberByEmailOrElseThrow(String email);
}
