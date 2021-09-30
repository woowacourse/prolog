package wooteco.prolog.studylog.ui;

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
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.report.application.StudylogService;
import wooteco.prolog.report.application.dto.CalendarStudylogResponse;
import wooteco.prolog.report.application.dto.MemberTagResponse;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class StudyLogOverviewController {

    private final StudylogService studylogService;
    private final MemberTagService memberTagService;

    @GetMapping("/{username}/tags")
    public ResponseEntity<MemberDataResponses<MemberTagResponse>> findTagsOfMine(
            @PathVariable String username) {
        return ResponseEntity.ok(MemberDataResponses.of(memberTagService.findByMember(username)));
    }

    @GetMapping("/{username}/calendar-posts")
    public ResponseEntity<MemberDataResponses<CalendarStudylogResponse>> findPostsOfMine(
            CalendarPostRequest calendarPostRequest, @PathVariable String username) {
        return ResponseEntity.ok(
            MemberDataResponses.of(
                        studylogService.findCalendarPosts(username, calendarPostRequest.localDate())
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
