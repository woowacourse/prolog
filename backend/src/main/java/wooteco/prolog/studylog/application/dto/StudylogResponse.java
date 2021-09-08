package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogResponse {

    private Long id;
    private MemberResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MissionResponse mission;
    private String title;
    private String content;
    private List<TagResponse> tags;

    public StudylogResponse(
            Studylog studylog,
            MissionResponse missionResponse,
            List<TagResponse> tagResponses) {
        this(
                studylog.getId(),
                MemberResponse.of(studylog.getMember()),
                studylog.getCreatedAt(),
                studylog.getUpdatedAt(),
                missionResponse,
                studylog.getTitle(),
                studylog.getContent(),
                tagResponses);
    }

    public static StudylogResponse of(Studylog studylog) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        List<TagResponse> tagResponses = toTagResponses(studylogTags);

        return new StudylogResponse(
                studylog.getId(),
                MemberResponse.of(studylog.getMember()),
                studylog.getCreatedAt(),
                studylog.getUpdatedAt(),
                MissionResponse.of(studylog.getMission()),
                studylog.getTitle(),
                studylog.getContent(),
                tagResponses
        );
    }

    private static List<TagResponse> toTagResponses(List<StudylogTag> studylogTags) {
        return studylogTags.stream()
                .map(StudylogTag::getTag)
                .map(TagResponse::of)
                .collect(toList());
    }
}
