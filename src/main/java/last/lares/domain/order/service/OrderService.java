package last.lares.domain.order.service;

import jakarta.transaction.Transactional;
import last.lares.domain.order.Order;
import last.lares.domain.order.presentation.dto.OrderCreateRequestDto;
import last.lares.domain.order.presentation.dto.OrderDto;
import last.lares.domain.order.presentation.dto.OrderListDto;
import last.lares.domain.order.repository.OrderRepository;
import last.lares.domain.user.User;
import last.lares.domain.user.repository.UserRepository;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderListDto allOrder() {
        List<Order> orderList = orderRepository.findAll();

        List<OrderListDto.OrderInfo>  orderInfoList = orderList.stream()
                .map(order -> OrderListDto.OrderInfo.builder()
                        .orderId(order.getOrderId())
                        .orderCreatedAt(order.getOrderCreatedAt())
                        .sendUserId(order.getSendUser().getUserId())
                        .receiveUserId(order.getReceiveUser().getUserId())
                        .receiveUserAddress(order.getReceiveUser().getUserAddress())
                        .orderItem(order.getOrderItem() + " / " + order.getOrderAmount())
                        .orderMemo(order.getOrderMemo())
                        .deliveryDate(order.getDeliveryDate())
                        .build()
                ).toList();

        return OrderListDto.builder()
                .orderInfoList(orderInfoList)
                .build();
    }

    @Transactional
    public OrderDto getOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 데이터입니다 : " + orderId));

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderCreatedAt(order.getOrderCreatedAt())
                .sendUserId(order.getSendUser().getUserId())
                .receiveUserId(order.getReceiveUser().getUserId())
                .orderItem(order.getOrderItem())
                .orderAmount(order.getOrderAmount())
                .orderMemo(order.getOrderMemo())
                .deliveryDate(order.getDeliveryDate())
                .build();
    }

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
