package wooteco.prolog.member.ui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;

@RestController
@RequestMapping("/members/me")
public class StudyLogOverviewController {

    @GetMapping("/tags")
    public ResponseEntity<MyTagResponses> findTagsOfMine(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok(MyTagResponses.of(MyTagResponse.dummyDataList()));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyTagResponses {
        private List<MyTagResponse> data;

        public static MyTagResponses of(List<MyTagResponse> myTagResponses) {
            return new MyTagResponses(myTagResponses);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class MyTagResponse {
        private int id;
        private String name;
        private int postCount;

        public static List<MyTagResponse> dummyDataList() {
            return Stream.iterate(0, n -> n+1)
                    .limit(10)
                    .map(n -> new MyTagResponse(n, "tag name "+n, n))
                    .collect(Collectors.toList());
        }
    }
}
