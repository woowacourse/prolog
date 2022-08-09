package wooteco.prolog.levellogs.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.member.application.dto.MemberResponse;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LevelLogSummaryResponse {

    private Long id;
    private String title;
    private MemberResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LevelLogSummaryResponse(LevelLog levelLog) {
        this(levelLog.getId(), levelLog.getTitle(), MemberResponse.of(levelLog.getMember()),
            levelLog.getCreatedAt(), levelLog.getUpdatedAt());
    }
}
