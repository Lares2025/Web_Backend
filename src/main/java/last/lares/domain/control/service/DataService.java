package last.lares.domain.control.service;

import jakarta.transaction.Transactional;
import last.lares.domain.control.ControlData;
import last.lares.domain.control.presentation.dto.DataListDto;
import last.lares.domain.control.repository.DataRepository;
import last.lares.domain.control.types.ControlType;
import last.lares.domain.robot.repository.RobotRepository;
import last.lares.domain.user.User;
import last.lares.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {
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
}
