package wooteco.prolog.image.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import wooteco.prolog.image.exception.UnsupportedFilExtensionException;

class FileExtensionTest {

    @Test
    void 파일_확장자를_반환한다() {
        final String fileName = "filename.jpg";

        final FileExtension fileExtension = FileExtension.from(fileName);

        assertThat(fileExtension).isEqualTo(FileExtension.JPG);
    }

    @Test
    void 지원하지_않는_확장자인_경우_예외를_발생한다() {
        final String fileName = "filename.jjj";

        assertThatThrownBy(() -> FileExtension.from(fileName))
                .isInstanceOf(UnsupportedFilExtensionException.class)
                .hasMessageContaining("지원하지 않는 확장자 입니다.");
    }
}
