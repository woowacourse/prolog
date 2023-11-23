package wooteco.support.autoceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MethodPatternTest {

    @ParameterizedTest
    @ValueSource(strings = {"/path1/param1/path2", "/path1/123123/path2"})
    void matching(String requestUri) {
        // given
        MethodPattern methodPattern = new MethodPattern(HttpMethod.GET, "/path1/*/path2");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        given(mockRequest.getMethod())
            .willReturn(HttpMethod.GET.name());
        given(mockRequest.getRequestURI())
            .willReturn(requestUri);

        // when
        boolean mathcing = methodPattern.match(mockRequest);

        // then
        assertThat(mathcing).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/path1/param1/path2/path3", "/path1/path2"})
    void notMatching(String requestUri) {
        // given
        MethodPattern methodPattern = new MethodPattern(HttpMethod.GET, "/path1/*/path2");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        given(mockRequest.getRequestURI())
            .willReturn(requestUri);

        // when
        boolean mathcing = methodPattern.match(mockRequest);

        // then
        assertThat(mathcing).isFalse();
    }
}
