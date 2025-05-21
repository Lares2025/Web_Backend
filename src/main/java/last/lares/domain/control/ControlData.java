package last.lares.domain.control;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import last.lares.domain.control.types.ControlType;
import last.lares.domain.robot.Robot;
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
@Table(name = "control_data_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class ControlData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int controlId;

    private LocalDate controlCreatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robotId")
    @JsonBackReference
    private Robot robot;

    private int controlAmount;

    @Enumerated(EnumType.STRING)
    private ControlType controlDirection;

    public String getControlCreatedAt() {
        return controlCreatedAt.format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }
}
