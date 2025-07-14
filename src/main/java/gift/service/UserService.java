package gift.service;

import gift.dto.UserRequestDto;
import gift.entity.User;
import gift.exception.EmailAlreadyExistsException;
import gift.exception.InvalidLoginException;
import gift.repository.H2UserRepository;
import gift.security.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final H2UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserService(H2UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidLoginException("해당 이메일이나 비밀번호로 가입된 계정이 없습니다."));
    }

    public String register(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.email()).isPresent())
            throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다. " + userRequestDto.email().value());

        User user = userRepository.save(userRequestDto.toEntity());
        return jwtProvider.generateToken(user);
    }

    public String login(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.email())
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidLoginException("해당 이메일이나 비밀번호로 가입된 계정이 없습니다."));

        if (!user.password().matches(userRequestDto.password())) {
            throw new InvalidLoginException("해당 이메일이나 비밀번호로 가입된 계정이 없습니다.");
        }

        return jwtProvider.generateToken(user);
    }
}
