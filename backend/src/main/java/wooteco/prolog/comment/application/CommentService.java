package wooteco.prolog.comment.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.comment.application.dto.CommentDeleteRequest;
import wooteco.prolog.comment.application.dto.CommentResponse;
import wooteco.prolog.comment.application.dto.CommentSaveRequest;
import wooteco.prolog.comment.application.dto.CommentSearchRequest;
import wooteco.prolog.comment.application.dto.CommentUpdateRequest;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.CommentType;
import wooteco.prolog.comment.domain.Content;
import wooteco.prolog.comment.domain.repository.CommentRepository;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.CommentNotFoundException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final StudylogRepository studylogRepository;
    private final LevelLogRepository levelLogRepository;

    public CommentService(final CommentRepository commentRepository,
                          final MemberRepository memberRepository,
                          final StudylogRepository studylogRepository,
                          final LevelLogRepository levelLogRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.studylogRepository = studylogRepository;
        this.levelLogRepository = levelLogRepository;
    }

    public Long insertComment(final CommentSaveRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(MemberNotFoundException::new);
        validateExistsPost(request.getPostId(), request.getCommentType());

        Comment comment = Comment.createComment(
            request.getPostId(),
            member,
            new Content(request.getContent()),
            request.getCommentType());

        return commentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse findComments(final CommentSearchRequest request) {
        validateExistsPost(request.getPostId(), request.getCommentType());

        List<CommentResponse> comments = commentRepository
            .findAllByPostIdAndCommentType(request.getPostId(), request.getCommentType())
            .stream()
            .map(CommentResponse::of)
            .collect(Collectors.toList());

        return new CommentsResponse(comments);
    }

    public void updateComment(final CommentUpdateRequest request) {
        validateExistsMember(request.getMemberId());
        Comment comment = commentRepository.findByCommentId(request.getCommentId())
            .orElseThrow(CommentNotFoundException::new);

        comment.update(new Content(request.getContent()));
    }

    public void deleteComment(final CommentDeleteRequest request) {
        validateExistsMember(request.getMemberId());

        Comment comment = commentRepository.findByCommentId(request.getCommentId())
            .orElseThrow(CommentNotFoundException::new);

        comment.delete();
    }

    private void validateExistsMember(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }

    private void validateExistsPost(final Long postId, final CommentType commentType) {
        if (commentType.equals(CommentType.STUDY_LOG) && !studylogRepository.existsById(postId)) {
            throw new StudylogNotFoundException();
        }

        if (commentType.equals(CommentType.LEVEL_LOG) && !levelLogRepository.existsById(postId)) {
            throw new LevelLogNotFoundException();
        }
    }
}
