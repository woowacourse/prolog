package wooteco.prolog.levellogs.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.member.application.dto.MemberResponse;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
