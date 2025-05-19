package last.lares.domain.control.presentation;

import last.lares.domain.control.presentation.dto.DataListDto;
import last.lares.domain.control.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
