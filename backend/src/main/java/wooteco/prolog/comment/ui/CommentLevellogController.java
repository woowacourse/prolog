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
import wooteco.prolog.comment.application.CommentLevellogService;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.ui.dto.CommentLevellogChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentLevellogCreateRequest;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
@RequestMapping("/levellogs")
public class CommentLevellogController {

    private final CommentLevellogService commentLevellogService;

    public CommentLevellogController(final CommentLevellogService commentLevellogService) {
        this.commentLevellogService = commentLevellogService;
    }

    @PostMapping("/{levellogId}/comments")
    public ResponseEntity<Void> createComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long levellogId,
                                              @RequestBody CommentLevellogCreateRequest request) {

        Long commentId = commentLevellogService.insertComment(
            request.toSaveRequest(loginMember.getId(), levellogId));

        return ResponseEntity.created(
            URI.create("/levellogs/" + levellogId + "/comments/" + commentId)).build();
    }

    @GetMapping("/{levellogId}/comments")
    public ResponseEntity<CommentsResponse> showComments(@PathVariable Long levellogId) {
        CommentsResponse commentsResponse = commentLevellogService.findComments(levellogId);

        return ResponseEntity.ok(commentsResponse);
    }

    @PutMapping("/{levellogId}/comments/{commentLevellogId}")
    public ResponseEntity<Void> changeComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long levellogId,
                                              @PathVariable Long commentLevellogId,
                                              @RequestBody CommentLevellogChangeRequest request) {
        commentLevellogService.updateComment(request.toUpdateRequest(
            loginMember.getId(), levellogId, commentLevellogId));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{levellogId}/comments/{commentLevellogId}")
    public ResponseEntity<Void> deleteComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long levellogId,
                                              @PathVariable Long commentLevellogId) {
        commentLevellogService.deleteComment(loginMember.getId(), levellogId, commentLevellogId);

        return ResponseEntity.noContent().build();
    }
}
