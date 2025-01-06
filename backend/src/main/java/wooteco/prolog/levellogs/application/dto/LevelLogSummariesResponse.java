package wooteco.prolog.levellogs.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class LevelLogSummariesResponse {

    private List<LevelLogSummaryResponse> data;
    private long totalSize;
    private int totalPage;
    private int currPage;
}
