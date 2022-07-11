package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.PopularStudylogService;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

@RestController
@RequestMapping("/studylogs")
@AllArgsConstructor
public class PopularStudylogController {

    private final PopularStudylogService popularStudylogService;

    /**
     * 갱신할 스터디로그 개수를 지정해야하기 때문에 pageable 필요 어드민 페이지를 붙이기 전에 편의상 METHOD 를 GET으로 함
     */
    @GetMapping("/popular/sync")
    public ResponseEntity<Void> updatePopularStudylogs(@PageableDefault Pageable pageable) {
        popularStudylogService.updatePopularStudylogs(pageable);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<PopularStudylogsResponse> showPopularStudylogs(@AuthMemberPrincipal LoginMember member, @PageableDefault Pageable pageable) {
        final PopularStudylogsResponse popularStudylogs = popularStudylogService
            .findPopularStudylogs(pageable, member.getId(), member.isAnonymous());
        return ResponseEntity.ok(popularStudylogs);
    }
}
