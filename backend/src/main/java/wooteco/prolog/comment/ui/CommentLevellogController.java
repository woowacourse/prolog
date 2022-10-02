package wooteco.prolog.comment.ui;

import static wooteco.prolog.comment.domain.CommentType.LEVEL_LOG;

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
import wooteco.prolog.comment.application.CommentService;
import wooteco.prolog.comment.application.dto.CommentDeleteRequest;
import wooteco.prolog.comment.application.dto.CommentSearchRequest;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.ui.dto.CommentChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentCreateRequest;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
@RequestMapping("/levellogs")
public class CommentLevellogController {

    private final CommentService commentService;

    public CommentLevellogController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{levellogId}/comments")
    public ResponseEntity<Void> createComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long levellogId,
                                              @RequestBody CommentCreateRequest request) {

        Long commentId = commentService.insertComment(
            request.toSaveRequest(levellogId, loginMember.getId(), LEVEL_LOG));

        return ResponseEntity.created(
            URI.create("/levellogs/" + levellogId + "/comments/" + commentId)).build();
    }

    @GetMapping("/{levellogId}/comments")
    public ResponseEntity<CommentsResponse> showComments(@PathVariable Long levellogId) {
        CommentsResponse commentsResponse = commentService.findComments(
            new CommentSearchRequest(levellogId, LEVEL_LOG));

        return ResponseEntity.ok(commentsResponse);
    }

    @PutMapping("/{levellogId}/comments/{commentId}")
    public ResponseEntity<Void> changeComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long levellogId,
                                              @PathVariable Long commentId,
                                              @RequestBody CommentChangeRequest request) {
        commentService.updateComment(request.toUpdateRequest(
            loginMember.getId(), commentId));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{levellogId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long levellogId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(new CommentDeleteRequest(loginMember.getId(), commentId));

        return ResponseEntity.noContent().build();
    }
}
