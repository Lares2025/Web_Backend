package last.lares.domain.robot.presentation;

import last.lares.domain.robot.presentation.dto.RobotListDto;
import last.lares.domain.robot.service.RobotService;
import last.lares.domain.robot.presentation.dto.RobotDto;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            return createInternalServerError();
        }
    }

    @GetMapping("/{robotId}")
    public ResponseEntity<?> readRobot(@PathVariable int robotId) {
        try {
            RobotListDto.RobotInfo response = robotService.readRobot(robotId);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            return createInternalServerError();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> newRobot(@RequestBody RobotDto request) {
        try {
            CommonResponseDto response = robotService.newRobot(request);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return createInternalServerError();
        }
    }

    @PatchMapping("/{robotId}")
    public ResponseEntity<?> updateRobot(@PathVariable int robotId, @RequestBody RobotDto robotDto) {
        try {
            CommonResponseDto response = robotService.updateRobot(robotId, robotDto);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            return createInternalServerError();
        }
    }

    @DeleteMapping("/{robotId}")
    public ResponseEntity<?> deleteRobot(@PathVariable int robotId) {
        try {
            CommonResponseDto response = robotService.deleteRobot(robotId);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            return createInternalServerError();
        }
    }

    private ResponseEntity<?> createInternalServerError() {
        CommonResponseDto response = CommonResponseDto.builder()
                .message("내부 서버 에러가 발생하였습니다.")
                .build();

        return ResponseEntity.internalServerError().body(response);
    }
}
