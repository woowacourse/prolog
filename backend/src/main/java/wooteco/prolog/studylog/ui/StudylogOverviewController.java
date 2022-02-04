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
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class StudylogOverviewController {

    private final StudylogService studylogService;
    private final MemberTagService memberTagService;

    @GetMapping("/{username}/tags")
    public ResponseEntity<MemberDataResponses<MemberTagResponse>> findTagsOfMine(
            @PathVariable String username) {
        return ResponseEntity.ok(MemberDataResponses.of(memberTagService.findByMember(username)));
    }

    @Deprecated
    @GetMapping("/{username}/calendar-posts")
    public ResponseEntity<MemberDataResponses<CalendarStudylogResponse>> findPostsOfMine(
        CalendarStudylogRequest calendarStudylogRequest, @PathVariable String username) {
        return ResponseEntity.ok(
            MemberDataResponses.of(
                        studylogService.findCalendarStudylogs(username, calendarStudylogRequest.localDate())
                )
        );
    }

    @GetMapping("/{username}/calendar-studylogs")
    public ResponseEntity<MemberDataResponses<CalendarStudylogResponse>> findStudylogsOfMine(
        CalendarStudylogRequest calendarStudylogRequest, @PathVariable String username) {
        return ResponseEntity.ok(
            MemberDataResponses.of(
                studylogService.findCalendarStudylogs(username, calendarStudylogRequest.localDate())
            )
        );
    }

    @Data
    public static class CalendarStudylogRequest {

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
