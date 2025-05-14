package last.lares.domain.robot.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewRobotDto {
    private String robotIp;
    private String robotName;
}
