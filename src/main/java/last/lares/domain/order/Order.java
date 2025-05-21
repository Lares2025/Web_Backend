package last.lares.domain.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import last.lares.domain.order.presentation.dto.OrderDto;
import last.lares.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@Entity
@Data
@Table(name = "order_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private LocalDate orderCreatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sendUserId")
    @JsonBackReference
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiveUserId")
    @JsonBackReference
    private User receiveUser;

    private String orderItem;

    private int orderAmount;

    private String orderMemo;

    private LocalDate deliveryDate;

    public String getOrderCreatedAt() {
        return orderCreatedAt.format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }

    public String getDeliveryDate() {
        return deliveryDate.format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }

    public void update(OrderDto request, User receiveUser) {
        setReceiveUser(receiveUser);
        setOrderItem(request.getOrderItem());
        setOrderAmount(request.getOrderAmount());
        setOrderMemo(request.getOrderMemo());
        setDeliveryDate(LocalDate.parse(request.getDeliveryDate(), DateTimeFormatter.ofPattern("yy년 MM월 dd일")));
    }
}
