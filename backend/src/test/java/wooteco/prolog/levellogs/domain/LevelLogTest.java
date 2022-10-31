package wooteco.prolog.levellogs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

public class LevelLogTest {

    @DisplayName("작성자인지 확인한다.")
    @Test
    void isAuthor() {
        Member another = new Member(2L, "another", "another", Role.NORMAL, 2L, "iamgeUrl");
        Member author = new Member(1L, "username", "nickname", Role.NORMAL, 1L, "iamgeUrl");
        LevelLog levelLog = new LevelLog(1L, "제목", "내용", author);

        assertThat(levelLog.isAuthor(author)).isTrue();
        assertThat(levelLog.isAuthor(another)).isFalse();
    }
}
