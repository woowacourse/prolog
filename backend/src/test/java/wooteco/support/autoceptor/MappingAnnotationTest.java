package wooteco.support.autoceptor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.support.autoceptor.scanner.MappingAnnotation;
import wooteco.support.autoceptor.scanner.MethodScanner;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class MappingAnnotationTest {

    @DisplayName("스프링 Mapping annotation 에서 Uri를 파싱한다.")
    @Test
    void extractUriFrom() {
        List<Method> methods = new MethodScanner(AuthMemberPrincipal.class)
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
        public void get(@AuthMemberPrincipal Member member) {

        }

        @PostMapping("/post")
        public void post(@AuthMemberPrincipal Member member) {

        }

        @DeleteMapping("/delete")
        public void delete(@AuthMemberPrincipal Member member) {

        }

        @PutMapping("/put")
        public void put(@AuthMemberPrincipal Member member) {

        }
    }
}
