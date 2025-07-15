package gift.repository.userRepository;

import gift.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findUserByEmail(String email);

    void deleteUserById(Long id);
}