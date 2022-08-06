package wooteco.prolog.levellogs.application.dto;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class LevelLogsResponse {

    private List<LevelLogResponse> data;
    private long totalSize;
    private int totalPage;
    private int currPage;

    public LevelLogsResponse(final List<LevelLogResponse> data, final long totalSize, final int totalPage,
                             final int currPage) {
        this.data = data;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
        this.currPage = currPage;
    }
}
