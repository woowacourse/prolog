package wooteco.prolog.studylog.ui;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.CommentService;
import wooteco.prolog.studylog.application.dto.CommentCreateRequest;
import wooteco.prolog.studylog.application.dto.CommentChangeRequest;
import wooteco.prolog.studylog.application.dto.CommentUpdateRequest;
import wooteco.prolog.studylog.application.dto.CommentsResponse;

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

    @GetMapping("/{studylogId}/comments")
    public ResponseEntity<CommentsResponse> showComments(@PathVariable Long studylogId) {
        CommentsResponse commentsResponse = commentService.findComments(studylogId);

        return ResponseEntity.ok(commentsResponse);
    }

    @PutMapping("/{studylogId}/comments/{commentId}")
    public ResponseEntity<Void> changeComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long studylogId,
                                              @PathVariable Long commentId,
                                              @RequestBody CommentChangeRequest request) {
        CommentUpdateRequest commentUpdateRequest = request.toRequest(loginMember.getId(), studylogId, commentId);
        commentService.updateComment(commentUpdateRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{studylogId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthMemberPrincipal LoginMember loginMember,
                                              @PathVariable Long studylogId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(loginMember.getId(), studylogId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
