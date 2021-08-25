package wooteco.prolog.member.ui;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
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
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.CalendarPostResponse;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.dto.MemberTagResponse;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class StudyLogOverviewController {

    private final TagService tagService;
    private final PostService postService;

    @GetMapping("/{username}/tags")
    public ResponseEntity<MemberDataResponses<MemberTagResponse>> findTagsOfMine(
            @PathVariable String username) {
        return ResponseEntity.ok(MemberDataResponses.of(tagService.findByMember(username)));
    }

    @GetMapping("/{username}/calendar-posts")
    public ResponseEntity<MemberDataResponses<CalendarPostResponse>> findPostsOfMine(
            CalendarPostRequest calendarPostRequest, @PathVariable String username) {
        return ResponseEntity.ok(
                MemberDataResponses.of(
                        postService.findCalendarPosts(username, calendarPostRequest.localDate())
                )
        );
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
}
