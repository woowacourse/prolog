package wooteco.prolog.comment.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

class CommentTest {

    @DisplayName("댓글의 삭제여부가 false일 경우, 삭제여부를 true로 변경할 수 있다.")
    @Test
    void commentIsDelete() {
        // given
        Member member = new Member("최지훈", "루키", Role.CREW, 1L, "image");
        Comment comment = new Comment(1L, member, "댓글의 내용");

        // when
        comment.delete();

        // then
        assertThat(comment.isDelete()).isTrue();
    }
}
