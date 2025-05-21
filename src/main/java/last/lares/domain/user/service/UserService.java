package last.lares.domain.user.service;

import last.lares.domain.user.User;
import last.lares.domain.user.presentation.dto.RegisterRequestDto;
import last.lares.domain.user.presentation.dto.UserListDto;
import last.lares.domain.user.repository.UserRepository;
import last.lares.domain.user.types.UserRole;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 모든 사용자 읽기
    @Transactional
    public UserListDto allUserList() {
        List<User> userList = userRepository.findAll();

        return createUserInfoList(userList);
    }

    // 회원 가입
    @Transactional
    public CommonResponseDto register(RegisterRequestDto request) {
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
                .userRole(UserRole.ROLE_USER)
                .userCreatedAt(LocalDate.now())
                .build();

        userRepository.save(user);

        return createCommonResponse("회원 가입에 성공하였습니다!");
    }

    // 특정 사용자 삭제
    @Transactional
    public CommonResponseDto deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다 : " + userId);
        }

        userRepository.deleteById(userId);

        return createCommonResponse("사용자가 정상적으로 삭제되었습니다!");
    }


    // 비밀번호 강도 로직 검사
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    private CommonResponseDto createCommonResponse(String message) {
        return CommonResponseDto.builder()
                .message(message)
                .build();
    }

    // UserListDto 생성
    private UserListDto createUserInfoList(List<User> userList) {
        List<UserListDto.UserInfo> userInfoList = userList.stream()
                .map(user -> UserListDto.UserInfo.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .userAddress(user.getUserAddress())
                        .userRole(user.getUserRole().name())
                        .userCreatedAt(user.getUserCreatedAt())
                        .build()
                ).toList();

        return UserListDto.builder()
                .userList(userInfoList)
                .build();
    }
}
