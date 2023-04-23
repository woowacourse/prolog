package wooteco.prolog.studylog;

import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.studylog.application.dto.TagRequest;

public class StudylogFixture {

    //Member
    // to do : null으로 표시한 부분 채우기
    public static Member TEST_MEMBER_CREW = new Member(1L, "홍혁준", "홍실",
        Role.CREW, 2L, null, null);

    //Keyword
    public static final Long SESSION_ID = 3L;
    public static final Keyword TEST_KEYWORD_JAVA = new Keyword(2L, "자바", "자바에 대한 설명", 1
        , 5, SESSION_ID, null, null);
    public static final Keyword TEST_KEYWORD_COLLECTIONS = new Keyword(3L, "컬렉션", "컬렉션 프레임워크에 대한 설명", 1
        , 5, SESSION_ID, null, null);

    //Session

    //Tag
    public static final TagRequest JAVA_TAG_REQUEST = new TagRequest("자바");
    public static final TagRequest COLLECTION_TAG_REQUEST = new TagRequest("컬렉션");
}
