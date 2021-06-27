package wooteco.prolog.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.post.exception.NotValidSortNameException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SortByTest {

    @DisplayName("정렬 명으로 정렬을 찾아온다.")
    @Test
    void findSortNameTest() {
        //given
        String asc = "ASC";
        String desc = "DESC";
        String wrongSortName = "Wrong Text";

        //when
        //then
        assertThat(SortBy.findByName(asc)).isEqualTo(SortBy.ASC);
        assertThat(SortBy.findByName(desc)).isEqualTo(SortBy.DESC);
        assertThatThrownBy(() -> SortBy.findByName(wrongSortName)).isInstanceOf(NotValidSortNameException.class);
    }
}