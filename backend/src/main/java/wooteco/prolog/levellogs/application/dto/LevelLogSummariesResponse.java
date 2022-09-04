package wooteco.prolog.levellogs.application.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
