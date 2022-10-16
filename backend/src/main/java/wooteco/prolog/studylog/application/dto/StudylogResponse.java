package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.ability.application.dto.AbilityResponse;
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
    private List<AbilityResponse> abilities;
    private boolean scrap;
    private boolean read;
    private int viewCount;
    private boolean liked;
    private int likesCount;
    private long commentCount;

    public StudylogResponse(
            Studylog studylog,
            SessionResponse sessionResponse,
            MissionResponse missionResponse,
            List<TagResponse> tagResponses,
            boolean liked, long commentCount) {
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
                Collections.emptyList(),
                false,
                false,
                studylog.getViewCount(),
                liked,
                studylog.getLikeCount(),
                commentCount
        );
    }

    public StudylogResponse(final Long id, final MemberResponse author, final LocalDateTime createdAt,
                            final LocalDateTime updatedAt,
                            final SessionResponse session, final MissionResponse mission, final String title,
                            final String content,
                            final List<TagResponse> tags,
                            final List<AbilityResponse> abilities, final boolean scrap, final boolean read,
                            final int viewCount, final boolean liked,
                            final int likesCount) {
        this.id = id;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.session = session;
        this.mission = mission;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.abilities = abilities;
        this.scrap = scrap;
        this.read = read;
        this.viewCount = viewCount;
        this.liked = liked;
        this.likesCount = likesCount;
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read, boolean liked) {
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
                Collections.emptyList(),
                scrap,
                read,
                studylog.getViewCount(),
                liked,
                studylog.getLikeCount(),
                0
        );
    }

    public static StudylogResponse of(Studylog studylog, List<AbilityResponse> abilityResponses, boolean scrap,
                                      boolean read, boolean liked, long commentCount) {
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
                abilityResponses,
                scrap,
                read,
                studylog.getViewCount(),
                liked,
                studylog.getLikeCount(),
                commentCount
        );
    }

    public static StudylogResponse of(Studylog studylog, List<AbilityResponse> abilityResponses, boolean scrap, boolean read, boolean liked) {
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
                abilityResponses,
                scrap,
                read,
                studylog.getViewCount(),
                liked,
                studylog.getLikeCount(),
                0
        );
    }

    // todo 정적팩토리메서드 from 사용해야하는데 of 쓰고 있는 부분 확인 후 리팩터링
    public static StudylogResponse of(Studylog studylog) {
        return of(studylog, false, false, null);
    }

    public static StudylogResponse of(Studylog studylog, List<AbilityResponse> abilityResponses) {
        return of(studylog, abilityResponses, false, false, null);
    }

    public static StudylogResponse of(Studylog studylog, List<AbilityResponse> abilityResponses, boolean scrap, boolean read, Long memberId) {
        return StudylogResponse.of(studylog, abilityResponses, scrap, read, studylog.likedByMember(memberId));
    }

    public static StudylogResponse of(Studylog studylog, Long memberId, long commentCount) {
        return of(studylog, false, false, memberId, commentCount);
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read, Long memberId, long commentCount) {
        return StudylogResponse.of(studylog, Collections.emptyList(), scrap, read, studylog.likedByMember(memberId), commentCount);
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read, Long memberId) {
        return StudylogResponse.of(studylog, Collections.emptyList(), scrap, read, studylog.likedByMember(memberId));
    }

    private static List<TagResponse> toTagResponses(List<StudylogTag> studylogTags) {
        return studylogTags.stream()
                .map(StudylogTag::getTag)
                .map(TagResponse::of)
                .collect(toList());
    }

    public static StudylogResponse of(Studylog studylog, boolean scrap, boolean read, boolean liked, Session session,
                                      Mission mission) {
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
                Collections.emptyList(),
                scrap,
                read,
                studylog.getViewCount(),
                liked,
                studylog.getLikeCount(),
                0
        );
    }

    public void setScrap(boolean isScrap) {
        this.scrap = isScrap;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
