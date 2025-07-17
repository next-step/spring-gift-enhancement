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
import gift.exception.userException.UserPasswordInputException;
import gift.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    @Transactional
    public String registerUser(UserRegisterDto dto) {
        String email = dto.email();
        String password = dto.password();
        UserRole role = dto.role();

        if (isEmailExist(email)) {
            throw new UserDuplicatedException();
        }

        User user = new User(null, email, password, role);
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser);

        return token;
    }

    private boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public String loginUser(UserLoginDto dto) {
        String targetEmail = dto.email();

        User findUser = findUserByEmail(targetEmail);

        if (findUser == null) {
            throw new UserNotFoundException(targetEmail);
        }
        if (!findUser.checkPassword(dto.password())) {
            throw new UserPasswordInputException();
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
    public Page<User> getUserList(String email, boolean isAdmin, Pageable pageable) {

        if (!isAdmin) {
            throw new UserAuthorizationException();
        }

        Page<User> users = getUsersByEmail(email, pageable);

        return users;
    }

    private Page<User> getUsersByEmail(String email, Pageable pageable) {
        if (email == null) {
            return userRepository.findAll(pageable);
        } else {
            Page<User> users = userRepository.findByEmailContaining(email, pageable);
            if (users.isEmpty()) {
                throw new UserNotFoundException();
            }
            return users;
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
    @Transactional
    public User updateUser(Long id, UserUpdateDto dto, boolean isAdmin) {
        if (!isAdmin) {
            throw new UserAuthorizationException();
        }

        User findUser = userRepository.findById(id).orElse(null);

        if (findUser == null) {
            throw new UserNotFoundException();
        }

        String email = dto.email();
        String password = dto.password();
        findUser.changeEmailAndPassword(email, password);

        return findUser;
    }


    @Override
    @Transactional
    public void deleteUserById(Long id, boolean isAdmin) {
        if (!isAdmin) {
            throw new UserAuthorizationException();
        }

        userRepository.deleteUserById(id);
    }

}
