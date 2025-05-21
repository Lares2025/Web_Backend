package last.lares.domain.order.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderListDto {
    private List<OrderInfo> orderInfoList;

    @Builder
    @Getter
    public static class OrderInfo {
        private int orderId;
        private String orderCreatedAt;
        private String sendUserId;
        private String receiveUserId;
        private String receiveUserAddress;
        private String orderItem;
        private String orderMemo;
        private String deliveryDate;
    }
}
