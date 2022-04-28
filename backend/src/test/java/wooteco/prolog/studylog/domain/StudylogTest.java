package wooteco.prolog.studylog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;

class StudylogTest {

    @DisplayName("좋아요와 조회를 기준으로 인기점수를 반환한다.")
    @Test
    void getPopularScore() {
        // given
        Member member = new Member("최현구", "현구막", Role.CREW, 1L, "image");
        Session session = new Session("세션 1");
        Mission mission = new Mission("자동차 미션", session);
        Tag tag1 = new Tag("Java");
        Tag tag2 = new Tag("Spring");
        Studylog studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));

        // when, then
        assertThat(studylog.getPopularScore()).isEqualTo(0);
        studylog.increaseViewCount();
        assertThat(studylog.getPopularScore()).isEqualTo(1);
        studylog.like(1L);
        assertThat(studylog.getPopularScore()).isEqualTo(3 + 1);
    }
}
