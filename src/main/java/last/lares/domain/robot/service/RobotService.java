package last.lares.domain.robot.service;

import last.lares.domain.robot.Robot;
import last.lares.domain.robot.presentation.dto.NewRobotDto;
import last.lares.domain.robot.repository.RobotRepository;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RobotService {
    private final RobotRepository robotRepository;

    @Transactional
    public CommonResponseDto newRobot(NewRobotDto request) {
        String robotIp = request.getRobotIp();
        if (robotRepository.existsById(robotIp)) {
            throw new IllegalArgumentException("이미 등록된 로봇입니다.");
        }

        Robot robot = Robot.builder()
                .robotIp(robotIp)
                .robotName(request.getRobotName())
                .robotCreatedAt(LocalDateTime.now())
                .build();

        robotRepository.save(robot);

        return createCommonResponse("신규 등록에 성공하였습니다!");
    }

    private CommonResponseDto createCommonResponse(String message) {
        return CommonResponseDto.builder()
                .message(message)
                .build();
    }
}
