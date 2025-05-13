package last.lares.domain.user.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterRequestDto {
    private String userId;
    private String userName;
    private String userPassword;
    private String userPasswordCheck;
    private String userAddress;
}
