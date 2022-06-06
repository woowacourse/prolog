package wooteco.prolog.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.domain.StudylogScrap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberScrapResponse {

    private MemberResponse memberResponse;
    private StudylogResponse studylogResponse;

    public static MemberScrapResponse of(StudylogScrap studylogScrap) {
        return new MemberScrapResponse(
            MemberResponse.of(studylogScrap.getMember()),
            StudylogResponse.of(studylogScrap.getStudylog())
        );
    }
}
