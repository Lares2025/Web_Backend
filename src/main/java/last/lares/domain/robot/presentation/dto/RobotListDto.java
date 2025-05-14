package last.lares.domain.robot.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RobotListDto {
    private List<RobotInfo> robotList;

    @Builder
    @Getter
    public static class RobotInfo {
        private String robotIp;
        private String robotName;
        private String robotCreatedAt;
    }
}
