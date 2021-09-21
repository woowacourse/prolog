package wooteco.prolog.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.MemberScrapStudylog;
import wooteco.prolog.studylog.application.dto.StudylogResponse;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberScrapResponse {

    private MemberResponse memberResponse;
    private StudylogResponse studylogResponse;

    public static MemberScrapResponse of(MemberScrapStudylog memberScrapStudylog) {
        return new MemberScrapResponse(
            MemberResponse.of(memberScrapStudylog.getMember()),
            StudylogResponse.of(memberScrapStudylog.getScrapStudylog())
        );
    }
}
