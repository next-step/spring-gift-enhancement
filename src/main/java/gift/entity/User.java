package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "USERS")
public record User(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id, @Column(nullable = false) String email, @Column(nullable = false) @NotNull String password,
                   @Enumerated(EnumType.STRING) UserRole role) {
    public boolean checkPassword(String password) {
        if (!this.password.equals(password)) {
            return false;
        }
        return true;
    }
}
