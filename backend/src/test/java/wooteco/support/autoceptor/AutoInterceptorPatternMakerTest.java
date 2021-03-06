package wooteco.support.autoceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.login.domain.AuthMemberPrincipal;

class AutoInterceptorPatternMakerTest {

    @DisplayName("URI Patterns를 반환한다.")
    @Test
    void extractPatterns() {
        // given
        AutoInterceptorPatternMaker maker = new AutoInterceptorPatternMaker(
            "wooteco.support.autoceptor.test_classes",
            AuthMemberPrincipal.class
        );

        // when
        List<String> patterns = maker.extractPatterns();

        //then
        assertThat(patterns).containsOnly(
            "/api2/test",
            "/api2/test/*"
        );
    }
}
