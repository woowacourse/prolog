package wooteco.prolog.image.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import wooteco.prolog.image.exception.FileNameEmptyException;

class FileNameTest {

    @Test
    void 파일명이_null인_경우_예외를_발생한다() {
        assertThatThrownBy(() -> new FileName(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void 파일명이_빈_경우_예외를_발생한다() {
        assertThatThrownBy(() -> new FileName(" "))
                .isInstanceOf(FileNameEmptyException.class)
                .hasMessage("파일 이름은 비어있을 수 없습니다.");
    }

    @Test
    void 파일명을_정상적으로_생성한다() {
        final String value = "test.jpg";
        final FileName fileName = new FileName(value);

        assertThat(fileName.getValue()).isEqualTo(value);
    }
}
