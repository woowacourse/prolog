package wooteco.prolog.article.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.article.application.OgTagParser.OgType;
import wooteco.prolog.article.application.dto.ArticleUrlRequest;
import wooteco.prolog.article.application.dto.ArticleUrlResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetaOgServiceTest {

    @Mock
    private OgTagParser ogTagParser;

    @InjectMocks
    private MetaOgService metaOgService;

    @DisplayName("링크에서 추출한 제목, 이미지를 반환한다.")
    @Test
    void parse() {
        //given
        final ArticleUrlRequest request = new ArticleUrlRequest("https://www.woowahan.com/");

        final Map<OgType, String> expectedParsedValue = new HashMap<>();
        expectedParsedValue.put(OgType.IMAGE, "이미지");
        expectedParsedValue.put(OgType.TITLE, "제목");

        when(ogTagParser.parse(any())).thenReturn(expectedParsedValue);

        //when
        final ArticleUrlResponse response = metaOgService.parse(request);

        //then
        assertThat(response.getTitle()).isEqualTo("제목");
        assertThat(response.getImageUrl()).isEqualTo("이미지");
    }
}
