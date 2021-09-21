package wooteco.prolog.studylog.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.application.MemberScrapService;
import wooteco.prolog.member.domain.Member;

@RequiredArgsConstructor
@RequestMapping("/posts")
public class StudylogMemberReactionController {

    private final MemberScrapService memberScrapService;

    @PostMapping("/{id}/scrap")
    public ResponseEntity<Void> registerScrap(@AuthMemberPrincipal Member member,
                                              @PathVariable Long id) {
        memberScrapService.insertScrap(member, id);
        return ResponseEntity.ok().build();
    }
}
