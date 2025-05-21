package last.lares.domain.order.service;

import jakarta.transaction.Transactional;
import last.lares.domain.order.Order;
import last.lares.domain.order.presentation.dto.OrderCreateRequestDto;
import last.lares.domain.order.repository.OrderRepository;
import last.lares.domain.user.User;
import last.lares.domain.user.repository.UserRepository;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CommonResponseDto newOrder(OrderCreateRequestDto request) {
        String sendUserId = request.getSendUserId();
        String receiveUserId = request.getReceiveUserId();

        User sendUser = userRepository.findById(sendUserId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 송신자입니다 : " + sendUserId));

        User receiveUser = userRepository.findById(receiveUserId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 수신자입니다 : " + receiveUserId));

        Order order = Order.builder()
                .orderCreatedAt(LocalDate.now())
                .sendUser(sendUser)
                .receiveUser(receiveUser)
                .orderItem(request.getOrderItem())
                .orderAmount(request.getOrderAmount())
                .orderMemo(request.getOrderMemo())
                .deliveryDate(LocalDate.parse(request.getDeliveryDate(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        orderRepository.save(order);

        return createCommonResponse("신규 등록에 성공하였습니다!");
    }

    private CommonResponseDto createCommonResponse(String message) {
        return CommonResponseDto.builder()
                .message(message)
                .build();
    }
}
