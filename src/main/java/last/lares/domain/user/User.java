package last.lares.domain.user;

import jakarta.persistence.*;
import last.lares.domain.user.types.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    private String userName;

    private String userPassword;

    private String userAddress;

    private UserRole userRole;

    private LocalDateTime userCreatedAt;
}
