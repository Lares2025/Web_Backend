package last.lares.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userId;
    private String userPassword;
}
