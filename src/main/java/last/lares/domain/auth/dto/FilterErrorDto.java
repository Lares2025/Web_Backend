package last.lares.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FilterErrorDto {
    private boolean status;
    private String message;
}
