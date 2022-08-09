package wooteco.prolog.levellogs.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LevelLogSummariesResponse {

    private List<LevelLogSummaryResponse> data;
    private long totalSize;
    private int totalPage;
    private int currPage;
}
