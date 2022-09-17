package wooteco.prolog.comment.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.comment.application.CommentStudylogService;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.ui.dto.CommentStudylogChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentStudylogCreateRequest;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
@RequestMapping("/studylogs")
public class CommentStudylogController {

    private final CommentStudylogService commentStudylogService;

    public CommentStudylogController(final CommentStudylogService commentStudylogService) {
        this.commentStudylogService = commentStudylogService;
    }

    @PostMapping("/{studylogId}/comments")
    public ResponseEntity<Void> createComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long studylogId,
                                              @RequestBody CommentStudylogCreateRequest request) {
        Long commentId = commentStudylogService.insertComment(
            request.toSaveRequest(loginMember.getId(), studylogId));

        return ResponseEntity.created(
            URI.create("/studylogs/" + studylogId + "/comments/" + commentId)).build();
    }

    @GetMapping("/{studylogId}/comments")
    public ResponseEntity<CommentsResponse> showComments(@PathVariable Long studylogId) {
        CommentsResponse commentsResponse = commentStudylogService.findComments(studylogId);

        return ResponseEntity.ok(commentsResponse);
    }

    @PutMapping("/{studylogId}/comments/{commentStudylogId}")
    public ResponseEntity<Void> changeComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long studylogId,
                                              @PathVariable Long commentStudylogId,
                                              @RequestBody CommentStudylogChangeRequest request) {
        commentStudylogService.updateComment(request.toUpdateRequest(
            loginMember.getId(), studylogId, commentStudylogId));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{studylogId}/comments/{commentStudylogId}")
    public ResponseEntity<Void> deleteComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long studylogId,
                                              @PathVariable Long commentStudylogId) {
        commentStudylogService.deleteComment(loginMember.getId(), studylogId, commentStudylogId);

        return ResponseEntity.noContent().build();
    }
}
