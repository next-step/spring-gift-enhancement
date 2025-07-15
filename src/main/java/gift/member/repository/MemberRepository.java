package gift.member.repository;

import gift.entity.Member;

import java.util.List;


public interface MemberRepository {
    public int addMember(Member member);
    public Member findMemberByIdOrElseThrow(Long id);
    public Member findMemberByEmail(String email);
    public Member findMemberByEmailOrElseThrow(String email);
    public List<Member> findAllMembers();
    public int updateMemberById(Member member);
    public int deleteMemberById(Long id);
}
