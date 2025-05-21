package last.lares.domain.order.presentation;

import last.lares.domain.order.presentation.dto.OrderCreateRequestDto;
import last.lares.domain.order.service.OrderService;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<?> newOrder(@RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        try {
            CommonResponseDto response = orderService.newOrder(orderCreateRequestDto);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message("내부 서버 에러가 발생하였습니다.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
