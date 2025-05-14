package last.lares.domain.robot.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewRobotDto {
    private int robotIp1;
    private int robotIp2;
    private int robotIp3;
    private int robotIp4;
    private String robotName;
}
