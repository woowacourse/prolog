package wooteco.prolog.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.post.exception.NotValidSortNameException;

class DirectionTest {

    @DisplayName("정렬 명으로 정렬을 찾아온다.")
    @Test
    void findSortNameTest() {
        //given
        String asc = "ASC";
        String desc = "DESC";
        String wrongSortName = "Wrong Text";

        //when
        //then
        assertThat(Direction.findByName(asc)).isEqualTo(Direction.ASC);
        assertThat(Direction.findByName(desc)).isEqualTo(Direction.DESC);
        assertThatThrownBy(() -> Direction.findByName(wrongSortName)).isInstanceOf(NotValidSortNameException.class);
    }
}