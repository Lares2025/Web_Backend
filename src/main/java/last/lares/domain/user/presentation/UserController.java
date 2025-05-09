package last.lares.domain.user.presentation;

import last.lares.domain.user.presentation.dto.RegisterDto;
import last.lares.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto.Request request) {
        try {
            RegisterDto.Response response = userService.register(request);

            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            RegisterDto.Response response = RegisterDto.Response.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            RegisterDto.Response response = RegisterDto.Response.builder()
                    .message("내부 서버 에러가 발생하였습니다.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
