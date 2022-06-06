package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogResponse {

    private Long id;
    private MemberResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SessionResponse session;
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
        SessionResponse sessionResponse,
        MissionResponse missionResponse,
        List<TagResponse> tagResponses,
        boolean liked) {
        this(
            studylog.getId(),
            MemberResponse.of(studylog.getMember()),
            studylog.getCreatedAt(),
            studylog.getUpdatedAt(),
            sessionResponse,
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

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read,
        boolean liked) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        List<TagResponse> tagResponses = toTagResponses(studylogTags);

        return new StudylogResponse(
            studylog.getId(),
            MemberResponse.of(studylog.getMember()),
            studylog.getCreatedAt(),
            studylog.getUpdatedAt(),
            SessionResponse.of(studylog.getSession()),
            MissionResponse.of(studylog.getMission()),
            studylog.getTitle(),
            studylog.getContent(),
            tagResponses,
            scrap,
            read,
            studylog.getViewCount(),
            liked,
            studylog.getLikeCount()
        );
    }

    // todo 정적팩토리메서드 from 사용해야하는데 of 쓰고 있는 부분 확인 후 리팩터링
    public static StudylogResponse of(Studylog studylog) {
        return of(studylog, false, false, null);
    }

    public static StudylogResponse of(Studylog studylog, Long memberId) {
        return of(studylog, false, false, memberId);
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read,
        Long memberId) {
        return StudylogResponse.of(studylog, scrap, read, studylog.likedByMember(memberId));
    }

    private static List<TagResponse> toTagResponses(List<StudylogTag> studylogTags) {
        return studylogTags.stream()
            .map(StudylogTag::getTag)
            .map(TagResponse::of)
            .collect(toList());
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read, boolean liked,
        Session session, Mission mission) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        List<TagResponse> tagResponses = toTagResponses(studylogTags);

        return new StudylogResponse(
            studylog.getId(),
            MemberResponse.of(studylog.getMember()),
            studylog.getCreatedAt(),
            studylog.getUpdatedAt(),
            SessionResponse.of(session),
            MissionResponse.of(mission),
            studylog.getTitle(),
            studylog.getContent(),
            tagResponses,
            scrap,
            read,
            studylog.getViewCount(),
            liked,
            studylog.getLikeCount()
        );
    }

    public static StudylogResponse of(Studylog studylog, Session session, Mission mission) {

        return StudylogResponse.of(studylog, false, false, false, session, mission);
    }

    public void setScrap(boolean isScrap) {
        this.scrap = isScrap;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
