package last.lares.domain.control.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DataListDto {
    private List<DataInfo>  dataInfoList;

    @Builder
    @Getter
    public static class DataInfo {
        private int controlId;
        private String controlCreatedAt;
        private String userId;
        private String robotName;
        private int controlAmount;
        private String controlDirection;
    }
}
