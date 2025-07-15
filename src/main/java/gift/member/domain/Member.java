package gift.member.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    protected Member() {}

    public Member(Long id, String email, String password, RoleType role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member(String email, String password, RoleType role) {
        this(null, email, password, role);
    }

    public Member(Long id){
        this(id, null, null, null);
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

    public RoleType getRole() {
        return role;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}