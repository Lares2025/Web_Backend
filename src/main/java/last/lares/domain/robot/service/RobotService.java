package last.lares.domain.robot.service;

import last.lares.domain.robot.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotService {
    private final RobotRepository repository;


}
