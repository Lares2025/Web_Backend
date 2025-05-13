package last.lares.domain.user.presentation;

import last.lares.domain.user.presentation.dto.RegisterRequestDto;
import last.lares.domain.user.presentation.dto.UserListDto;
import last.lares.domain.user.service.UserService;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> allUserList() {
        try {
            UserListDto response = userService.allUserList();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("내부 서버 에러가 발생하였습니다.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto request) {
        try {
            CommonResponseDto response = userService.register(request);

            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message("내부 서버 에러가 발생하였습니다.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            CommonResponseDto response = userService.deleteUser(userId);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message("내부 서버 에러가 발생하였습니다.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
