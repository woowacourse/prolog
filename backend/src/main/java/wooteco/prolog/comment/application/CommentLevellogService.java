package wooteco.prolog.comment.application;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.comment.application.dto.CommentLevellogUpdateReqeust;
import wooteco.prolog.comment.application.dto.CommentLevellogSaveRequest;
import wooteco.prolog.comment.application.dto.CommentResponse;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.CommentLevellog;
import wooteco.prolog.comment.domain.repository.CommentLevellogRepository;
import wooteco.prolog.comment.exception.CommentLevellogNotFoundException;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;

@Transactional
@Service
public class CommentLevellogService {

    private final CommentLevellogRepository commentLevelogRepository;
    private final MemberRepository memberRepository;
    private final LevelLogRepository levelLogRepository;

    public CommentLevellogService(final CommentLevellogRepository commentLevelogRepository,
                                  final MemberRepository memberRepository,
                                  final LevelLogRepository levelLogRepository) {
        this.commentLevelogRepository = commentLevelogRepository;
        this.memberRepository = memberRepository;
        this.levelLogRepository = levelLogRepository;
    }

    public Long insertComment(final CommentLevellogSaveRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(MemberNotFoundException::new);
        validateExistsLevelog(request.getLevelogId());

        CommentLevellog comment = new CommentLevellog(
            null,
            request.getLevelogId(),
            new Comment(null, member, request.getContent()));

        return commentLevelogRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse findComments(final Long levellogId) {
        validateExistsLevelog(levellogId);

        List<CommentResponse> comments = commentLevelogRepository
            .findAllByLevellogId(levellogId)
            .stream()
            .map(comment -> CommentResponse.of(comment.getComment()))
            .collect(Collectors.toList());

        return new CommentsResponse(comments);
    }

    public void updateComment(final CommentLevellogUpdateReqeust request) {
        validateExistsMember(request.getMemberId());
        validateExistsLevelog(request.getLevellogId());

        CommentLevellog commentLevelog = commentLevelogRepository
            .findByCommentId(request.getCommentLevellogId())
            .orElseThrow(CommentLevellogNotFoundException::new);

        commentLevelog.updateContent(request.getContent());
    }

    public void deleteComment(final Long memberId, final Long levellogId, final Long commentLevellogId) {
        validateExistsMember(memberId);
        validateExistsLevelog(levellogId);

        CommentLevellog commentLevelog = commentLevelogRepository
            .findByCommentId(commentLevellogId)
            .orElseThrow(CommentLevellogNotFoundException::new);

        commentLevelog.deleteComment();
    }

    private void validateExistsMember(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }

    private void validateExistsLevelog(final Long levelogId) {
        if (!levelLogRepository.existsById(levelogId)) {
            throw new LevelLogNotFoundException();
        }
    }
}
