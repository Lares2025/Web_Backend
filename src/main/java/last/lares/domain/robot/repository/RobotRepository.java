package last.lares.domain.robot.repository;

import last.lares.domain.robot.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends JpaRepository<Robot, String> {
}
