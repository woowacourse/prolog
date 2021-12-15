package wooteco.prolog.studylog.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StudylogLikeResponse {

    private boolean liked;
    private Integer likesCount;

}
