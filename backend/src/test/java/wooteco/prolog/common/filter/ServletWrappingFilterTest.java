package wooteco.prolog.common.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServletWrappingFilterTest {

    private final ServletWrappingFilter servletWrappingFilter = new ServletWrappingFilter();

    @DisplayName("ServletRequest를 프록시 객체로 변경한다.")
    @Test
    void doFilter() throws IOException, ServletException {
        String targetMessage = String.join("\r\n", "This message is", "test");

        ServletRequest mockServletRequest = mock(ServletRequest.class);

        MockFilterChain mockFilterChain = new MockFilterChain();
        servletWrappingFilter.doFilter(mockServletRequest, null, mockFilterChain);

        assertThat(mockServletRequest).isNotSameAs(mockFilterChain.getServletRequest());
    }

    private class MockFilterChain implements FilterChain {

        private ServletRequest servletRequest;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
            this.servletRequest = request;
        }

        public ServletRequest getServletRequest() {
            return servletRequest;
        }
    }
}