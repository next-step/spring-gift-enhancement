package gift.entity;

import gift.member.dto.MemberResponseDto;
import gift.member.dto.MemberUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String role;

    public MemberResponseDto toMemberResponseDto() {
        return new MemberResponseDto(this);
    }

    protected Member(){}

    public Member(Long id, String email, String password, String name, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Member(Long id, MemberUpdateRequestDto requestDto) {
        this(id, requestDto.email(), null, requestDto.name(), requestDto.role());
    }

    public void update(Long id, MemberUpdateRequestDto requestDto) {
        this.id = id;
        this.email = requestDto.email();
        this.name = requestDto.name();
        this.role = requestDto.role();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
