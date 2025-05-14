package last.lares.domain.robot.presentation;

import last.lares.domain.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/robot", produces = "application/json")
@RequiredArgsConstructor
public class RobotController {
    private final RobotService robotService;
}
