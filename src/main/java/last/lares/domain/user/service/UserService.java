package last.lares.domain.user.service;

import last.lares.domain.user.User;
import last.lares.domain.user.presentation.dto.RegisterDto;
import last.lares.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 가입
    @Transactional
    public RegisterDto.Response register(RegisterDto.Request request) {
        String userId = request.getUserId();
        if (userRepository.existsById(userId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String rowPassword = request.getUserPassword();
        if (!rowPassword.equals(request.getUserPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else if (!isPasswordStrong(rowPassword)) {
            throw new IllegalArgumentException("비밀번호가 충분히 강력하지 않습니다.");
        }

        String userPassword = bCryptPasswordEncoder.encode(rowPassword);

        User user = User.builder()
                .userId(userId)
                .userName(request.getUserName())
                .userPassword(userPassword)
                .userAddress(request.getUserAddress())
                .userCreatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return RegisterDto.Response.builder()
                .message("회원 가입에 성공하였습니다!")
                .build();
    }

    // 비밀번호 강도 로직 검사
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }
}
