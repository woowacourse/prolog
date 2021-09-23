package wooteco.support.autoceptor;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import wooteco.prolog.member.domain.LoginMember;
import wooteco.support.autoceptor.scanner.MappingAnnotation;
import wooteco.support.autoceptor.scanner.MethodScanner;
import wooteco.support.security.core.AuthenticationPrincipal;

class MappingAnnotationTest {

    @DisplayName("스프링 Mapping annotation 에서 Uri를 파싱한다.")
    @Test
    void extractUriFrom() {
        List<Method> methods = new MethodScanner(AuthenticationPrincipal.class)
            .extractMethodAnnotatedOnParameter(UriTest.class);
        List<String> uris = methods.stream()
            .map(MappingAnnotation::extractUriFrom)
            .flatMap(Collection::stream)
            .collect(toList());

        assertThat(uris).containsOnly(
            "/get", "/post", "/delete", "/put"
        );
    }

    private static class UriTest {

        @GetMapping("/get")
        public void get(@AuthenticationPrincipal LoginMember member) {

        }

        @PostMapping("/post")
        public void post(@AuthenticationPrincipal LoginMember member) {

        }

        @DeleteMapping("/delete")
        public void delete(@AuthenticationPrincipal LoginMember member) {

        }

        @PutMapping("/put")
        public void put(@AuthenticationPrincipal LoginMember member) {

        }
    }
}
