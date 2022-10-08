package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.domain.StudylogTemp;
import wooteco.prolog.studylog.domain.StudylogTempTags;

@NoArgsConstructor
@Getter
public class StudylogTempResponse {

    private Long id;
    private MemberResponse author;
    private String title;
    private String content;
    private SessionResponse session;
    private MissionResponse mission;
    private List<TagResponse> tags;
    private List<AbilityResponse> abilities;

    private StudylogTempResponse(MemberResponse author, String title, String content, SessionResponse session,
                                 MissionResponse mission, List<TagResponse> tags, List<AbilityResponse> abilities) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.session = session;
        this.mission = mission;
        this.tags = tags;
        this.abilities = abilities;
    }

    public static StudylogTempResponse from(StudylogTemp studylogTemp, List<AbilityResponse> abilityResponse) {
        return new StudylogTempResponse(
                MemberResponse.of(studylogTemp.getMember()),
                studylogTemp.getTitle(),
                studylogTemp.getContent(),
                SessionResponse.of(studylogTemp.getSession()),
                MissionResponse.of(studylogTemp.getMission()),
                toTagResponses(studylogTemp.getStudylogTempTags()),
                abilityResponse);
    }

    //todo TagResponse의 정적팩토리메서드로 리팩터링
    private static List<TagResponse> toTagResponses(StudylogTempTags tags) {
        return tags.getValues().stream()
                .map(tag -> TagResponse.of(tag.getTag()))
                .collect(toList());
    }

    public static StudylogTempResponse toNull() {
        return new StudylogTempResponse(null, null, null, null, null, null, null);
    }
}
