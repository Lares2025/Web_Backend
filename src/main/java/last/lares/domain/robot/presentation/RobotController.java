package last.lares.domain.robot.presentation;

import last.lares.domain.robot.presentation.dto.RobotListDto;
import last.lares.domain.robot.service.RobotService;
import last.lares.domain.robot.presentation.dto.NewRobotDto;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/robot", produces = "application/json")
@RequiredArgsConstructor
public class RobotController {
    private final RobotService robotService;

    @GetMapping("/")
    public ResponseEntity<?> allRobot() {
        try {
            RobotListDto response = robotService.allRobot();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("내부 서버 에러가 발생하였습니다.");
        }
    }
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
