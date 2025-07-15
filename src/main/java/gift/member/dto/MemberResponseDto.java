package gift.member.dto;

import gift.entity.Member;

public record MemberResponseDto (
        Long id,
        String email,
        String name,
        String role
) {
    public MemberResponseDto(Member member) {
        this(member.id(), member.email(), member.name(), member.role());
    }
}
