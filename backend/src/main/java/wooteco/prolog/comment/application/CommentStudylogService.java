package wooteco.prolog.comment.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.comment.application.dto.CommentResponse;
import wooteco.prolog.comment.application.dto.CommentStudylogSaveRequest;
import wooteco.prolog.comment.application.dto.CommentStudylogUpdateRequest;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.CommentStudylog;
import wooteco.prolog.comment.domain.repository.CommentStudylogRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Transactional
@Service
public class CommentStudylogService {

    private final CommentStudylogRepository studylogCommentRepository;
    private final MemberRepository memberRepository;
    private final StudylogRepository studylogRepository;

    public CommentStudylogService(final CommentStudylogRepository studylogCommentRepository,
                                  final MemberRepository memberRepository,
                                  final StudylogRepository studylogRepository) {
        this.studylogCommentRepository = studylogCommentRepository;
        this.memberRepository = memberRepository;
        this.studylogRepository = studylogRepository;
    }

    public Long insertComment(final CommentStudylogSaveRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(MemberNotFoundException::new);
        validateExistsStudylog(request.getStudylogId());

        CommentStudylog comment = new CommentStudylog(
            null,
            request.getStudylogId(),
            new Comment(null, member, request.getContent()));

        return studylogCommentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse findComments(final Long studylogId) {
        validateExistsStudylog(studylogId);

        List<CommentResponse> comments = studylogCommentRepository
            .findAllByStudylogId(studylogId)
            .stream()
            .map(comment -> CommentResponse.of(comment.getComment()))
            .collect(Collectors.toList());

        return new CommentsResponse(comments);
    }

    public void updateComment(final CommentStudylogUpdateRequest request) {
        validateExistsMember(request.getMemberId());
        validateExistsStudylog(request.getStudylogId());

        CommentStudylog studylogComment = studylogCommentRepository
            .findByCommentId(request.getStudylogCommentId())
            .orElseThrow(StudylogNotFoundException::new);

        studylogComment.updateContent(request.getContent());
    }

    public void deleteComment(final Long memberId, final Long studylogId, final Long studylogCommentId) {
        validateExistsMember(memberId);
        validateExistsStudylog(studylogId);

        CommentStudylog studylogComment = studylogCommentRepository
            .findByCommentId(studylogCommentId)
            .orElseThrow(StudylogNotFoundException::new);

        studylogComment.deleteComment();
    }

    private void validateExistsMember(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }

    private void validateExistsStudylog(final Long studylogId) {
        if (!studylogRepository.existsById(studylogId)) {
            throw new StudylogNotFoundException();
        }
    }
}
