package wooteco.prolog.studylog.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StudylogTagsTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
        "https://avatars.githubusercontent.com/u/51393021?v=4");
    private static final Studylog 웨지가_쓴_글 = new Studylog(웨지, "제목", "내용",
        new Mission("[BE] 글쓰기 미션", new Session("세션1")), Lists.emptyList());
    private static final Tag 워니_태그 = new Tag("워니");
    private static final StudylogTag 워니_스터디로그_태그 = new StudylogTag(1L, 웨지가_쓴_글, 워니_태그);

    private StudylogTags studylogTags;

    @BeforeEach
    void setUp() {
        this.studylogTags = new StudylogTags();
        this.studylogTags.add(Collections.singletonList(워니_스터디로그_태그));
    }

    @DisplayName("StudylogTag가 add 될 때 중복된 이름의 Tag는 배제된다.")
    @Test
    void addTest() {
        // given
        Tag 워니_태그 = new Tag("워니");
        Tag 세션1_태그 = new Tag("세션1");

        StudylogTag 워니_스터디로그_태그 = new StudylogTag(2L, 웨지가_쓴_글, 워니_태그);
        StudylogTag 세션1_스터디로그_태그 = new StudylogTag(3L, 웨지가_쓴_글, 세션1_태그);

        List<StudylogTag> studylogTags = Arrays.asList(워니_스터디로그_태그, 세션1_스터디로그_태그);

        // when
        this.studylogTags.add(studylogTags);

        // then
        assertThat(this.studylogTags.getValues()).containsExactlyInAnyOrder(
            StudylogTagsTest.워니_스터디로그_태그,
            세션1_스터디로그_태그);
    }

    @DisplayName("update로 StudylogTag 리스트를 교체한다.")
    @Test
    void updateTest() {
        // given
        Tag 포비_태그 = new Tag("포비");
        Tag 세션1_태그 = new Tag("세션1");

        StudylogTag 포비_스터디로그_태그 = new StudylogTag(2L, 웨지가_쓴_글, 포비_태그);
        StudylogTag 세션1_스터디로그_태그 = new StudylogTag(3L, 웨지가_쓴_글, 세션1_태그);

        List<StudylogTag> studylogTags = Arrays.asList(포비_스터디로그_태그, 세션1_스터디로그_태그);

        // when
        this.studylogTags.update(studylogTags);

        // then
        assertThat(this.studylogTags.getValues()).containsExactlyInAnyOrder(포비_스터디로그_태그,
            세션1_스터디로그_태그);
    }
}
