package wooteco.prolog.studylog.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.CommentService;
import wooteco.prolog.studylog.application.dto.CommentCreateRequest;

@RestController
@RequestMapping("/studylogs")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{studylogId}/comments")
    public ResponseEntity<Void> createComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long studylogId,
                                              @RequestBody CommentCreateRequest request) {
        Long commentId = commentService.insertComment(
            request.toRequest(loginMember.getId(), studylogId));

        return ResponseEntity.created(
            URI.create("/studylogs/" + studylogId + "/comments/" + commentId)).build();
    }
}
