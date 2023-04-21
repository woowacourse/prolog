package wooteco.prolog.studylog.domain;

import org.junit.jupiter.api.Assertions;
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

class StudylogTest {

    private Member member;
    private Studylog studylog;
    private Tag tag1;
    private Tag tag2;

    @BeforeEach
    void beforeEach() {
        member = new Member(1L,"최현구", "현구막", Role.CREW, 1L, "image");
        Session session = new Session("세션 1");
        Mission mission = new Mission("자동차 미션", session);
        tag1 = new Tag("Java");
        tag2 = new Tag("Spring");
        studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));
    }

    @Test
    @DisplayName("스터리로그가 본인의 것이라면 예외가 발생하지 않는다.")
    void validateBelongTo_success() {
        // given
        Long selfMemberId = 1L;

        // when, then
        Assertions.assertDoesNotThrow(() -> studylog.validateBelongTo(selfMemberId));
    }

    @Test
    @DisplayName("스터디로그가 본인의 것이 아니라면 예외를 반환한다.")
    void validateBelongTo_fail() {
        // given
        Long otherMemberId = 2L;

        // when, then
        assertThatThrownBy(() -> studylog.validateBelongTo(otherMemberId))
            .isInstanceOf(AuthorNotValidException.class);
    }

    @Test
    @DisplayName("스터디로그를 정상적으로 업데이트한다.")
    void update(){
        // given
        Tags tags  = new Tags(Arrays.asList(new Tag("new JAVA"), new Tag("new Spring")));
        List<StudylogTag> studylogTag = tags.getList().stream()
            .map(tag -> new StudylogTag(studylog, tag))
            .collect(Collectors.toList());

        // when
        studylog.update("수정된 제목","수정된 내용", tags);

        // then
        assertThat(studylog.getTitle()).isEqualTo("수정된 제목");
        assertThat(studylog.getContent()).isEqualTo("수정된 내용");
        assertThat(studylog.getStudylogTags()).isEqualTo(studylogTag);
    }

    @Test
    @DisplayName("스터디로그에 정상적으로 태그를 추가한다.")
    void addTags() {
        // given
        Tag newTag1 = new Tag("new JAVA");
        Tag newTag2 = new Tag("new Spring");
        Tags tags  = new Tags(Arrays.asList(newTag1, newTag2));

        // when
        studylog.addTags(tags);

        // then
        assertThat(studylog.getStudylogTags()).hasSize(4);
    }

    @Test
    @DisplayName("본인이 아닌 다른 멤버가 스터디로그 조회 시 조회 수가 증가한다.")
    void increaseViewCount() {
        // given
        Member otherMember = new Member(2L, "김동해", "오션", Role.CREW, 2L, "image");

        // when, then
        studylog.increaseViewCount(member);
        assertThat(studylog.getViewCount()).isEqualTo(0);

        studylog.increaseViewCount(otherMember);
        assertThat(studylog.getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("본인의 스터디로그인지에 따라 boolean 값을 반환한다.")
    void isMine(){
        // given
        Member otherMember = new Member(3L, "문채원", "라온", Role.CREW, 3L, "image");

        // when, then
        assertThat(studylog.isMine(member)).isTrue();
        assertThat(studylog.isMine(otherMember)).isFalse();
    }

    @Test
    @DisplayName("스터디로그 좋아요 시 좋아요 값이 증가하고, 특정 멤버가 눌렀는지 여부에 따라 boolean값을 반환한다.")
    void like(){
        // when, then
        studylog.like(1L);
        assertThat(studylog.getLikeCount()).isEqualTo(1);
        assertThat(studylog.likedByMember(1L)).isTrue();

        studylog.unlike(1L);
        assertThat(studylog.getLikeCount()).isEqualTo(0);
        assertThat(studylog.likedByMember(1L)).isFalse();
    }

    @DisplayName("좋아요와 조회를 기준으로 인기점수를 반환한다.")
    @Test
    void getPopularScore() {
        // when, then
        assertThat(studylog.getPopularScore()).isEqualTo(0);
        studylog.increaseViewCount();
        assertThat(studylog.getPopularScore()).isEqualTo(1);
        studylog.like(1L);
        assertThat(studylog.getPopularScore()).isEqualTo(3 + 1);
    }

    @Test
    @DisplayName("스터디로그의 title을 반환한다.")
    void getTitle() {
        // when, then
        assertThat(studylog.getTitle()).isEqualTo("제목");
        studylog.delete();
        assertThat(studylog.getTitle()).isEqualTo("삭제된 학습로그");
    }

    @Test
    @DisplayName("스터디로그의 content를 반환한다.")
    void getContent() {
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

        // when, then
        assertThat(studylog.getStudylogTags()).isEqualTo(studylogTags);
    }
}
