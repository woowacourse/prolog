package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.domain.StudylogTags;
import wooteco.prolog.studylog.domain.StudylogTemp;

@NoArgsConstructor
@Getter
public class StudylogTempResponse {

    private Long id;
    private MemberResponse author;
    private String title;
    private String content;
    private MissionResponse mission;
    private List<TagResponse> tags;

    private StudylogTempResponse(MemberResponse author, String title, String content,
        MissionResponse mission, List<TagResponse> tags) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.mission = mission;
        this.tags = tags;
    }

    public static StudylogTempResponse from(StudylogTemp studylogTemp) {
        return new StudylogTempResponse(
            MemberResponse.of(studylogTemp.getMember()),
            studylogTemp.getTitle(),
            studylogTemp.getContent(),
            MissionResponse.of(studylogTemp.getMission()),
            toTagResponses(studylogTemp.getStudylogTags()));
    }

    //todo TagResponse의 정적팩토리메서드로 리팩터링
    private static List<TagResponse> toTagResponses(StudylogTags tags) {
        return tags.getValues().stream()
            .map(tag -> TagResponse.of(tag.getTag()))
            .collect(toList());
    }

    public static StudylogTempResponse toNull() {
        return new StudylogTempResponse(null, null, null, null, null);
    }
}
