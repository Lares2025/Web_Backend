package last.lares.domain.control.presentation;

import last.lares.domain.control.presentation.dto.DataListDto;
import last.lares.domain.control.service.DataService;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/control")
@RequiredArgsConstructor
public class ControlController {
    private final DataService dataService;

    @GetMapping("/")
    public ResponseEntity<?> allData() {
        try {
            DataListDto response = dataService.allData();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("내부 서버 에러가 발생하였습니다.");
        }
    }

    @DeleteMapping("/{controlId}")
    public ResponseEntity<?> deleteData(@PathVariable int controlId) {
        try {
            CommonResponseDto response = dataService.deleteData(controlId);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("내부 서버 에러가 발생하였습니다.");
        }
    }
}
