package last.lares.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private boolean status;
    private String message;
    private Data data;

    @Builder
    @Getter
    public static class Data {
        private String userId;
        private String userName;
        private String userRole;
    }
}
