package last.lares.domain.control.presentation;

import last.lares.domain.control.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/control")
@RequiredArgsConstructor
public class ControlController {
    private final DataService dataService;
}
