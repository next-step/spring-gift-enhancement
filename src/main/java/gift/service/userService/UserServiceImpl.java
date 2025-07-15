package gift.service.userService;

import gift.Jwt.JwtUtil;
import gift.dto.userDto.UserLoginDto;
import gift.dto.userDto.UserRegisterDto;
import gift.dto.userDto.UserUpdateDto;
import gift.entity.User;
import gift.entity.UserRole;
import gift.exception.userException.UserAuthorizationException;
import gift.exception.userException.UserDuplicatedException;
import gift.exception.userException.UserNotFoundException;
import gift.exception.userException.UserPasswordException;
import gift.repository.userRepository.UserRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositoryJPA userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepositoryJPA userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String registerUser(UserRegisterDto dto) {
        String email = dto.email();
        String password = dto.password();
        UserRole role = dto.role();

        if (findUserByEmail(email)!= null) {
            throw new UserDuplicatedException();
        }
        User user = new User(null, email, password, role);
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser);

        return token;
    }

    @Override
    public String loginUser(UserLoginDto dto) {
        String targetEmail = dto.email();

        User findUser = findUserByEmail(targetEmail);
        if (findUser == null) {
            throw new UserNotFoundException(targetEmail);
        }
        if (!findUser.checkPassword(dto.password())) {
            throw new UserPasswordException();
        }
        return jwtUtil.generateToken(findUser);

    }

    @Override
    public User findUserByEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public List<User> getUserList(String email,boolean isAdmin) {

        if (!isAdmin) {
            throw new UserAuthorizationException();
        }

        List<User> users = getUsersByEmail(email);

        return users;
    }

    private List<User> getUsersByEmail(String email) {
        if (email == null) {
            return userRepository.findAll();
        } else {
            User findUser = findUserByEmail(email);
            if (findUser == null) {
                throw new UserNotFoundException();
            } else {
                return List.of(findUser);
            }
        }
    }

    @Override
    public User finUserById(Long id) {
        User user = findUserById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    private User findUserById(Long id) {
        User user = userRepository.findUserById(id);
        return user;
    }

    @Override
    public User updateUser(Long id, UserUpdateDto dto, boolean isAdmin) {
        if (!isAdmin) {
            throw new UserAuthorizationException();
        }

        User findUser = userRepository.findById(id).orElse(null);

        if (findUser == null) {
            throw new UserNotFoundException();
        }

        findUser.setEmail(dto.email());
        findUser.setPassword(dto.password());

        return findUser;
    }

    @Override
    public void deleteUserById(Long id,boolean isAdmin) {
        if (!isAdmin) {
            System.out.println("권한이 없습니다.");
            throw new UserAuthorizationException();
        }

        userRepository.deleteUserById(id);
    }

}
