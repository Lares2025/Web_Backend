package last.lares.domain.robot.service;

import last.lares.domain.robot.Robot;
import last.lares.domain.robot.presentation.dto.RobotDto;
import last.lares.domain.robot.presentation.dto.RobotListDto;
import last.lares.domain.robot.repository.RobotRepository;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RobotService {
    private final RobotRepository robotRepository;

    @Transactional
    public RobotListDto allRobot() {
        List<Robot> robotList = robotRepository.findAll();

        return createRobotInfoList(robotList);
    }

    @Transactional
    public CommonResponseDto newRobot(RobotDto request) {
        Robot robot = Robot.builder()
                .robotIp1(request.getRobotIp1())
                .robotIp2(request.getRobotIp2())
                .robotIp3(request.getRobotIp3())
                .robotIp4(request.getRobotIp4())
                .robotName(request.getRobotName())
                .robotCreatedAt(LocalDateTime.now())
                .build();

        robotRepository.save(robot);

        return createCommonResponse("신규 등록에 성공하였습니다!");
    }

    @Transactional
    public CommonResponseDto updateRobot(int robotId, RobotDto request) {
        Robot robot = robotRepository.findById(robotId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 로봇입니다 : " + robotId));

        robot.update(request);

        return createCommonResponse("로봇이 정상적으로 수정되었습니다!");
    }

    @Transactional
    public CommonResponseDto deleteRobot(int robotId) {
        if (!robotRepository.existsById(robotId)) {
            throw new UsernameNotFoundException("존재하지 않는 로봇입니다 : " + robotId);
        }

        robotRepository.deleteById(robotId);

        return createCommonResponse("로봇이 정상적으로 삭제되었습니다!");
    }

    private CommonResponseDto createCommonResponse(String message) {
        return CommonResponseDto.builder()
                .message(message)
                .build();
    }

    private RobotListDto createRobotInfoList(List<Robot> robotList) {
        List<RobotListDto.RobotInfo> robotInfoList = robotList.stream()
                .map(robot -> RobotListDto.RobotInfo.builder()
                        .robotId(robot.getRobotId())
                        .robotIp(robot.getRobotIp())
                        .robotName(robot.getRobotName())
                        .robotCreatedAt(robot.getRobotCreatedAt())
                        .build()
                ).toList();

        return RobotListDto.builder()
                .robotList(robotInfoList)
                .build();
    }
}
