package wooteco.prolog.studylog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.exception.AuthorNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StudylogTest {
    private static final Long SELF_MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    private Member member;
    private Studylog studylog;
    private Tag tag1;
    private Tag tag2;
    private Mission mission;

    @BeforeEach
    void beforeEach() {
        member = new Member(SELF_MEMBER_ID, "최현구", "현구막", Role.CREW, 1L, "image");
        mission = new Mission("자동차 미션", new Session("세션 1"));
        tag1 = new Tag("Java");
        tag2 = new Tag("Spring");
    }

    @Test
    @DisplayName("스터리로그가 본인의 것이라면 예외가 발생하지 않는다.")
    void validateBelongTo_success() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertDoesNotThrow(() -> studylog.validateBelongTo(SELF_MEMBER_ID));
    }

    @Test
    @DisplayName("스터디로그를 정상적으로 업데이트한다.")
    void update() {
        // given
        Tags tags = new Tags(Arrays.asList(new Tag("new JAVA"), new Tag("new Spring")));
        List<StudylogTag> studylogTag = tags.getList().stream()
            .map(tag -> new StudylogTag(studylog, tag))
            .collect(Collectors.toList());
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when
        studylog.update("수정된 제목", "수정된 내용", tags);

        // then
        assertAll(() -> {
            assertThat(studylog.getTitle()).isEqualTo("수정된 제목");
            assertThat(studylog.getContent()).isEqualTo("수정된 내용");
            assertThat(studylog.getStudylogTags()).isEqualTo(studylogTag);
        });
    }

    @Test
    @DisplayName("스터디로그가 본인의 것이 아니라면 예외를 반환한다.")
    void validateBelongTo_fail() {
        //given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThatThrownBy(() -> studylog.validateBelongTo(OTHER_MEMBER_ID))
            .isInstanceOf(AuthorNotValidException.class);
    }

    @Test
    @DisplayName("스터디로그에 정상적으로 태그를 추가한다.")
    void addTags() {
        // given
        Tag newTag1 = new Tag("new JAVA");
        Tag newTag2 = new Tag("new Spring");
        Tags tags = new Tags(Arrays.asList(newTag1, newTag2));
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when
        studylog.addTags(tags);

        // then
        assertThat(studylog.getStudylogTags()).hasSize(4);
    }

    @Test
    @DisplayName("본인이 스터디로그 조회 시 조회 수가 증가한다.")
    void increaseViewCount_byCurrentMember() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        studylog.increaseViewCount(member);
        assertThat(studylog.getViewCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("본인이 아닌 다른 멤버가 스터디로그 조회 시 조회 수가 증가한다.")
    void increaseViewCount_byOtherMember() {
        // given
        Member otherMember = new Member(OTHER_MEMBER_ID, "김동해", "오션", Role.CREW, 2L, "image");
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        studylog.increaseViewCount(otherMember);
        assertThat(studylog.getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("본인의 스터디로그인지에 따라 boolean 값을 반환한다.")
    void isMine() {
        // given
        Member otherMember = new Member(3L, "문채원", "라온", Role.CREW, 3L, "image");
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThat(studylog.isMine(member)).isTrue();
        assertThat(studylog.isMine(otherMember)).isFalse();
    }

    @Test
    @DisplayName("스터디로그 좋아요 시 좋아요 값이 증가하고, 특정 멤버가 눌렀는지 여부에 따라 boolean값을 반환한다.")
    void like() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        studylog.like(SELF_MEMBER_ID);
        assertThat(studylog.getLikeCount()).isEqualTo(1);
        assertThat(studylog.likedByMember(SELF_MEMBER_ID)).isTrue();
    }

    @Test
    @DisplayName("스터디로그 좋아요 취소 시 좋아요 값이 감소하고, 특정 멤버가 눌렀는지 여부에 따라 boolean값을 반환한다.")
    void unlike() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        studylog.like(SELF_MEMBER_ID);
        studylog.unlike(SELF_MEMBER_ID);
        assertThat(studylog.getLikeCount()).isEqualTo(0);
        assertThat(studylog.likedByMember(SELF_MEMBER_ID)).isFalse();
    }

    @DisplayName("좋아요와 조회를 기준으로 인기점수를 반환한다.")
    @Test
    void getPopularScore() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThat(studylog.getPopularScore()).isEqualTo(0);
        studylog.increaseViewCount();
        assertThat(studylog.getPopularScore()).isEqualTo(1);
        studylog.like(SELF_MEMBER_ID);
        assertThat(studylog.getPopularScore()).isEqualTo(3 + 1);
    }

    @Test
    @DisplayName("스터디로그의 title을 반환한다.")
    void getTitle() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThat(studylog.getTitle()).isEqualTo("제목");
        studylog.delete();
        assertThat(studylog.getTitle()).isEqualTo("삭제된 학습로그");
    }

    @Test
    @DisplayName("스터디로그의 content를 반환한다.")
    void getContent() {
        // given
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThat(studylog.getContent()).isEqualTo("내용");
        studylog.delete();
        assertThat(studylog.getContent()).isEqualTo("삭제된 학습로그입니다.");
    }

    @Test
    @DisplayName("스터디로그의 studylogTags를 반환한다.")
    void getStudylogTags() {
        // given
        StudylogTag studylogTag1 = new StudylogTag(studylog, tag1);
        StudylogTag studylogTag2 = new StudylogTag(studylog, tag2);
        List<StudylogTag> studylogTags = Arrays.asList(studylogTag1, studylogTag2);
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThat(studylog.getStudylogTags()).isEqualTo(studylogTags);
    }
}
