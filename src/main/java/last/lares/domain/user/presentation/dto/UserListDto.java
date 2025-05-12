package last.lares.domain.user.presentation.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import last.lares.domain.user.User;
import last.lares.domain.user.types.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class UserListDto {
    private List<UserInfo> userList;

    @Builder
    @Getter
    private static class UserInfo {
        private String userId;
        private String userName;
        private String userAddress;
        private String userRole;
        private String userCreatedAt;
    }
}
