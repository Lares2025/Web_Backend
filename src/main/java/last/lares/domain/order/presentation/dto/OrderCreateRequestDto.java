package last.lares.domain.order.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class OrderCreateRequestDto {
    private String receiveUserId;
    private String orderItem;
    private int orderAmount;
    private String orderMemo;
    private String deliveryDate;
}
