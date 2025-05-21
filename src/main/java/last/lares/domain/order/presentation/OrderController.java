package last.lares.domain.order.presentation;

import last.lares.domain.order.presentation.dto.OrderCreateRequestDto;
import last.lares.domain.order.presentation.dto.OrderDto;
import last.lares.domain.order.presentation.dto.OrderListDto;
import last.lares.domain.order.service.OrderService;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<?> allOrder() {
        try {
            OrderListDto orderListDto = orderService.allOrder();

            return ResponseEntity.ok().body(orderListDto);
        } catch (Exception e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message("내부 서버 에러가 발생하였습니다.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") int orderId) {
        try {
            OrderDto orderDto = orderService.getOrder(orderId);

            return ResponseEntity.ok().body(orderDto);
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

    @PatchMapping("/")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) {
        try {
            CommonResponseDto response = orderService.updateOrder(orderDto);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            CommonResponseDto responseDto = CommonResponseDto.builder()
                    .message("내부 서버 에러가 발생하였습니다 : " + e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId) {
        try {
            CommonResponseDto response = orderService.deleteOrder(orderId);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            CommonResponseDto responseDto = CommonResponseDto.builder()
                    .message("내부 서버 에러가 발생하였습니다 : " + e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
}
