package last.lares.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import last.lares.domain.control.ControlData;
import last.lares.domain.order.Order;
import last.lares.domain.user.types.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Entity
@Data
@Table(name = "user_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userId;

    private String userName;

    private String userPassword;

    private String userAddress;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private LocalDate userCreatedAt;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<ControlData> controlDataList;

    @OneToMany(
            mappedBy = "sendUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Order> sendOrderList;

    @OneToMany(
            mappedBy = "receiveUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Order> receiveOrderList;

    public String getUserCreatedAt() {
        return userCreatedAt.format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }
}
