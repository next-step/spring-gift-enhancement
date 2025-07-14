package gift.service;

import gift.dto.UserRequestDto;
import gift.dto.UserResponseDto;
import gift.entity.User;
import gift.exception.DecryptFailedException;
import gift.exception.EncryptFailedException;
import gift.exception.ProductNotFoundException;
import gift.exception.UserNotFoundException;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<UserResponseDto> findAllUsers(){
        return userRepository.findAllUsers().stream()
                .map(user -> {
                    try {
                        return new UserResponseDto(
                                user.id(),
                                authService.decryptAES(user.email()),
                                user.password(),
                                user.createdDate()
                        );
                    } catch (Exception e) {
                        throw new DecryptFailedException();
                    }
                })
                .collect(Collectors.toList());
    }

    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        String email;

        // email 복호화
        try {
            email = authService.decryptAES(user.email());
        } catch (Exception e) {
            throw new DecryptFailedException();
        }

        return new UserResponseDto(user.id(), email, user.password(), user.createdDate());
    }

    public void deleteUser(Long id){
        boolean flag = userRepository.deleteUser(id);
        if(!flag) {
            throw new ProductNotFoundException(id);
        }
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto){

        User user;

        try {
            user = new User(
                    authService.encryptAES(userRequestDto.email()),
                    authService.encryptSHA256(userRequestDto.password()));
        }
        catch (Exception e) {throw new EncryptFailedException();
        }

        boolean flag = userRepository.updateUser(id, user);

        // 수정됐는지 검증
        if(!flag) {
            throw new UserNotFoundException(id);
        }

        user = userRepository.findUserById(id);
        return new UserResponseDto(user);
    }
}
