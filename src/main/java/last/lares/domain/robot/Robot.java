package last.lares.domain.robot;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Data
@Table(name = "robot_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class Robot {
    @Id
    private String robotIp;

    private String robotName;

    private LocalDateTime robotCreatedAt;
}
