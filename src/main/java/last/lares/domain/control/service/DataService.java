package last.lares.domain.control.service;

import jakarta.transaction.Transactional;
import last.lares.domain.control.ControlData;
import last.lares.domain.control.presentation.dto.DataListDto;
import last.lares.domain.control.repository.DataRepository;
import last.lares.domain.control.types.ControlType;
import last.lares.domain.robot.Robot;
import last.lares.domain.robot.repository.RobotRepository;
import last.lares.domain.user.User;
import last.lares.domain.user.repository.UserRepository;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {
    private final UserRepository userRepository;
    private final RobotRepository robotRepository;
    private final DataRepository dataRepository;

    @Transactional
    public DataListDto allData() {
        List<ControlData> controlDataList = dataRepository.findAll();

        List<DataListDto.DataInfo> dataInfoList = controlDataList.stream()
                .map(data -> DataListDto.DataInfo.builder()
                        .controlId(data.getControlId())
                        .controlCreatedAt(data.getControlCreatedAt())
                        .userId(data.getUser().getUserId())
                        .robotName(data.getRobot().getRobotName())
                        .controlAmount(data.getControlAmount())
                        .controlDirection(data.getControlDirection().name())
                        .build())
                .toList();

        return DataListDto.builder()
                .dataInfoList(dataInfoList)
                .build();
    }

    @Transactional
    public void newData(String userId, int robotId, int controlAmount, ControlType controlDirection) {
        // 추후 명확한 제어 요청 DTO ( ControlRequestDto ) 생성 시, 본 메서드 인자값 형태 대체 예정

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다 : " + userId));

        Robot robot = robotRepository.findById(robotId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 로봇입니다 : " + robotId));

        ControlData controlData = ControlData.builder()
                .controlCreatedAt(LocalDate.now())
                .user(user)
                .robot(robot)
                .controlAmount(controlAmount)
                .controlDirection(controlDirection)
                .build();

        dataRepository.save(controlData);
    }

    @Transactional
    public CommonResponseDto deleteData(int controlId) {
        if (!dataRepository.existsById(controlId)) {
            throw new UsernameNotFoundException("존재하지 않는 데이터입니다 : " + controlId);
        }

        dataRepository.deleteById(controlId);

        return CommonResponseDto.builder()
                .message("데이터가 정상적으로 삭제되었습니다!")
                .build();
    }
}
