package last.lares.domain.control.repository;

import last.lares.domain.control.ControlData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Integer, ControlData> {
}
