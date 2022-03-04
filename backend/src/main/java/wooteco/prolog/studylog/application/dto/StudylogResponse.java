package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
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
    private boolean scrap;
    private boolean read;
    private int viewCount;
    private boolean liked;
    private int likesCount;

    public StudylogResponse(
        Studylog studylog,
        MissionResponse missionResponse,
        List<TagResponse> tagResponses,
        boolean liked) {
        this(
            studylog.getId(),
            MemberResponse.of(studylog.getMember()),
            studylog.getCreatedAt(),
            studylog.getUpdatedAt(),
            missionResponse,
            studylog.getTitle(),
            studylog.getContent(),
            tagResponses,
            false,
            false,
            studylog.getViewCount(),
            liked,
            studylog.getLikeCount()
        );
    }

    public static StudylogResponse of(Studylog studylog) {
        return of(studylog, false, false, null);
    }

    public static StudylogResponse of(Studylog studylog, Long memberId) {
        return of(studylog, false, false, memberId);
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read, Long memberId) {
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
            tagResponses,
            scrap,
            read,
            studylog.getViewCount(),
            studylog.likedByMember(memberId),
            studylog.getLikeCount()
        );
    }

    private static List<TagResponse> toTagResponses(List<StudylogTag> studylogTags) {
        return studylogTags.stream()
            .map(StudylogTag::getTag)
            .map(TagResponse::of)
            .collect(toList());
    }

    public void setScrap(boolean isScrap){
        this.scrap = isScrap;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setLiked(boolean isLiked) {
        this.liked = isLiked;
    }
}
