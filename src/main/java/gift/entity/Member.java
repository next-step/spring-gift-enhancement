package gift.entity;

import gift.member.dto.MemberResponseDto;
import gift.member.dto.MemberUpdateRequestDto;

public record Member(Long id, String email, String password, String name, String role){
    public MemberResponseDto toMemberResponseDto(){
        return new MemberResponseDto(this);
    }

    public Member(Long id, MemberUpdateRequestDto requestDto) {
        this(id, requestDto.email(), null, requestDto.name(), requestDto.role());
    }
}
