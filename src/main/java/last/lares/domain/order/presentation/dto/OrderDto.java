package last.lares.domain.order.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDto {
    private int orderId;
    private String orderCreatedAt;
    private String sendUserId;
    private String receiveUserId;
    private String orderItem;
    private int orderAmount;
    private String orderMemo;
    private String deliveryDate;
}
