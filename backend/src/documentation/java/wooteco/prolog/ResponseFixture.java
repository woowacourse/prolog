package wooteco.prolog;

import static wooteco.prolog.member.domain.Role.NORMAL;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.CommentMemberResponse;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentsResponse;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

public class ResponseFixture {

    public static final SessionResponse SESSION_RESPONSE =
            new SessionResponse(1L, "세션1");

    public static final MissionResponse MISSION_RESPONSE1 =
            new MissionResponse(1L, "지하철 노선도 미션", SESSION_RESPONSE);

    public static final MissionResponse MISSION_RESPONSE2 =
            new MissionResponse(1L, "로또 미션", SESSION_RESPONSE);

    public static final MemberResponse MEMBER_RESPONSE = new MemberResponse(
            1L,
            "soulG",
            "잉",
            NORMAL,
            "https://avatars.githubusercontent.com/u/52682603?v=4"
    );

    public static final StudylogResponse STUDYLOG_RESPONSE1 = new StudylogResponse(
            1L,
            MEMBER_RESPONSE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            SESSION_RESPONSE,
            MISSION_RESPONSE1,
            "JAVA",
            "Spring Data JPA를 학습함.",
            Arrays.asList(new TagResponse(3L, "java"), new TagResponse(4L, "jpa")),
            Collections.emptyList(),
            false,
            false,
            0,
            false,
            0
    );

    private static final StudylogResponse STUDYLOG_RESPONSE2 = new StudylogResponse(
            2L,
            MEMBER_RESPONSE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            SESSION_RESPONSE,
            MISSION_RESPONSE2,
            "SPA",
            "SPA 방식으로 앱을 구현하였음.\nrouter 를 구현 하여 이용함.\n",
            Arrays.asList(new TagResponse(1L, "spa"), new TagResponse(2L, "router")),
            Collections.emptyList(),
            false,
            false,
            0,
            false,
            0
    );

    public static final StudylogsResponse STUDYLOGS_RESPONSE = new StudylogsResponse(Arrays.asList(
            STUDYLOG_RESPONSE1,
            STUDYLOG_RESPONSE2
    ), 2L, 1, 1);

    public static final String COMMENT = "댓글의 내용입니다.";
    public static final CommentsResponse COMMENTS_RESPONSE = new CommentsResponse(Arrays.asList(
            new CommentResponse(1L,
                    new CommentMemberResponse(1L, "yboy", "잉",
                            "https://avatars.githubusercontent.com/u/52682603?v=4", "CREW")
                    , COMMENT,
                    LocalDateTime.now())
    ));

    public static final Member MEMBER
            = new Member("yboy", "잉", Role.NORMAL, 1L, GithubResponses.소롱.getAvatarUrl());

    public static final TagResponse TAG_RESPONSE1 = new TagResponse(1L, "자바");
    public static final TagResponse TAG_RESPONSE2 = new TagResponse(2L, "코틀린");

    public static final List<TagResponse> TAG_RESPONSES = Arrays.asList(
            TAG_RESPONSE1,
            TAG_RESPONSE2
    );

    public static final List<MemberTagResponse> MEMBER_TAB_RESPONSES = Arrays.asList(
            new MemberTagResponse(TAG_RESPONSE1, 2),
            new MemberTagResponse(TAG_RESPONSE2, 2)
    );

    public static final List<CalendarStudylogResponse> CALENDER_STUDYLOG_RESPONSES = Arrays.asList(
            new CalendarStudylogResponse(1L, "instanceOf()를 지양하자", LocalDateTime.now().minusMonths(2),
                    LocalDateTime.now()),
            new CalendarStudylogResponse(2L, "JPA 쿼리 개선기", LocalDateTime.now().minusMonths(1),
                    LocalDateTime.now())
    );

    public static final List<StudylogRssFeedResponse> STUDYLOG_RSS_FEED_RESPONSES = Arrays.asList(
            new StudylogRssFeedResponse("Prolog | 우아한테크코스 학습로그 저장소", "자바", "잉", "http://localhost:8080",
                    Date.from(Instant.now())));
}
