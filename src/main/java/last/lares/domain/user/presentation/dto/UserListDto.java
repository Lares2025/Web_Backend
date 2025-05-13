package last.lares.domain.user.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserListDto {
    private List<UserInfo> userList;

    @Builder
    @Getter
    public static class UserInfo {
        private String userId;
        private String userName;
        private String userAddress;
        private String userRole;
        private String userCreatedAt;
    }
}
