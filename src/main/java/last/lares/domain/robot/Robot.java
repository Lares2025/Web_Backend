package last.lares.domain.robot;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import last.lares.domain.control.ControlData;
import last.lares.domain.robot.presentation.dto.RobotDto;
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
@Table(name = "robot_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int robotId;

    private int robotIp1;

    private int robotIp2;

    private int robotIp3;

    private int robotIp4;

    private String robotName;

    private LocalDate robotCreatedAt;

    @OneToMany(
            mappedBy = "robot",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<ControlData> controlDataList;

    public String getRobotIp() {
        return robotIp1 + "." + robotIp2 + "." + robotIp3 + "." + robotIp4;
    }

    public String getRobotCreatedAt() {
        return robotCreatedAt.format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }

    public void update(RobotDto request) {
        setRobotIp1(request.getRobotIp1());
        setRobotIp2(request.getRobotIp2());
        setRobotIp3(request.getRobotIp3());
        setRobotIp4(request.getRobotIp4());
        setRobotName(request.getRobotName());
    }
}
