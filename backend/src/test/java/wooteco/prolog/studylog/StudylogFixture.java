package wooteco.prolog.studylog;

import java.util.Collections;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.domain.Studylog;

public class StudylogFixture {

    //Member
    // to do : null으로 표시한 부분 채우기
    public static Member TEST_MEMBER_CREW1 = new Member(1L, "홍혁준", "홍실",
        Role.CREW, 2L, null, null);
    public static Member TEST_MEMBER_CREW2 = new Member(2L, "송세연", "아마란스",
        Role.CREW, 2L, null, null);

    //Keyword
    public static final Long SESSION_ID = 3L;
    public static final Keyword TEST_KEYWORD_JAVA = new Keyword(2L, "자바", "자바에 대한 설명", 1
        , 5, SESSION_ID, null, null);
    public static final Keyword TEST_KEYWORD_COLLECTIONS = new Keyword(3L, "컬렉션", "컬렉션 프레임워크에 대한 설명", 1
        , 5, SESSION_ID, TEST_KEYWORD_JAVA, null);

    //Session
    public static final Session TEST_SESSION = new Session(4L, 5L, "세션");

    //Tag
    public static final TagRequest JAVA_TAG_REQUEST = new TagRequest("자바");
    public static final TagRequest COLLECTION_TAG_REQUEST = new TagRequest("컬렉션");

    //Mission
    public static final Mission TEST_MISSION = new Mission(6L, "레벨 2 - 웹 자동차 경주", TEST_SESSION);

    //Studylog
    public static final Studylog TEST_STUDYLOG = new Studylog(TEST_MEMBER_CREW1, "레벨 1 레벨인터뷰", "레벨인터뷰에 대한 내용입니다."
        , TEST_MISSION, Collections.emptyList());
}
