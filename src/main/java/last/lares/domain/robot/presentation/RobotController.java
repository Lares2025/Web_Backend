package last.lares.domain.robot.presentation;

import last.lares.domain.robot.service.RobotService;
import last.lares.domain.robot.presentation.dto.NewRobotDto;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/robot", produces = "application/json")
@RequiredArgsConstructor
public class RobotController {
    private final RobotService robotService;

    @PostMapping("/")
    public ResponseEntity<?> newRobot(@RequestBody NewRobotDto request) {
        try {
            CommonResponseDto response = robotService.newRobot(request);

            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message("내부 에러가 발생하였습니다.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
