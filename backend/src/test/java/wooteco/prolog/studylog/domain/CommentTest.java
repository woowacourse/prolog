package wooteco.prolog.studylog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.exception.CommentDeleteException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentTest {

    @Test
    @DisplayName("isdelete가 true일 때 예외를 발생한다.")
    void delete() {
        //given
        Member member = new Member("최현구", "현구막", Role.CREW, 1L, "image");
        Session session = new Session("세션 1");
        Mission mission = new Mission("자동차 미션", session);
        Tag tag1 = new Tag("Java");
        Tag tag2 = new Tag("Spring");
        Studylog studylog = new Studylog(member, "제목", "내용", mission, Arrays.asList(tag1, tag2));
        Comment comment = new Comment(1L, member, studylog, "댓글이다.");
        comment.delete();

        assertThatThrownBy(() -> comment.delete())
            .isInstanceOf(CommentDeleteException.class);
    }
}
