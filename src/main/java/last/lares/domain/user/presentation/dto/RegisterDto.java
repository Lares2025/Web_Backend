package last.lares.domain.user.presentation.dto;

import lombok.Builder;
import lombok.Getter;

public class RegisterDto {
    @Builder
    @Getter
    public static class Request {
        private String userId;
        private String userName;
        private String userPassword;
        private String userPasswordCheck;
        private String userAddress;
    }

    @Builder
    @Getter
    public static class Response {
        private String message;
    }
}
