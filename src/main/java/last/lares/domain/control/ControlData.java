package last.lares.domain.control;

import jakarta.persistence.*;
import last.lares.domain.control.types.ControlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime controlCreatedAt;

    private String userId;

    private int robotId;

    private int controlAmount;

    private ControlType controlDirection;
}
