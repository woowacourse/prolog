package wooteco.prolog.member.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.dto.MemberTagResponse;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class StudyLogOverviewController {

    private final TagService tagService;

    @GetMapping("/{username}/tags")
    public ResponseEntity<MemberDataResponses<MemberTagResponse>> findTagsOfMine(
            @PathVariable String username) {
        return ResponseEntity.ok(MemberDataResponses.of(tagService.findByMember(username)));
    }

    @GetMapping("/{username}/calendar-posts")
    public ResponseEntity<MemberDataResponses<MemberPostResponse>> findPostsOfMine(
            CalendarPostRequest calendarPostRequest, @PathVariable String username) {
        return ResponseEntity.ok(MemberDataResponses
                .of(MemberPostResponse.dummyDataList(calendarPostRequest.localDate())));
    }

    @Data
    public static class CalendarPostRequest {

        private int year = LocalDate.now().getYear();
        private Month month = LocalDate.now().getMonth();

        public LocalDate localDate() {
            return LocalDate.of(year, month, 1);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberDataResponses<T> {

        private List<T> data;

        public static <T> MemberDataResponses<T> of(List<T> data) {
            return new MemberDataResponses<>(data);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class MemberPostResponse {

        private Long id;
        private String title;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static List<MemberPostResponse> dummyDataList(LocalDate localDate) {
            return Stream.iterate(0, n -> n + 1)
                    .limit(10)
                    .map(n -> {
                        Long id = n.longValue();
                        final LocalDateTime localDateTime = LocalDateTime
                                .of(localDate, LocalTime.NOON).plusDays(n);
                        final String title = "타이틀 " + n;
                        return new MemberPostResponse(id, title, localDateTime, localDateTime);
                    })
                    .collect(Collectors.toList());
        }
    }
}
