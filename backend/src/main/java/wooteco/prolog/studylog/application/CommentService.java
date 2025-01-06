package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentSaveRequest;
import wooteco.prolog.studylog.application.dto.CommentUpdateRequest;
import wooteco.prolog.studylog.application.dto.CommentsResponse;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.CommentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.util.List;
import java.util.stream.Collectors;

import static wooteco.prolog.common.exception.BadRequestCode.COMMENT_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;

@Transactional
@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final StudylogRepository studylogRepository;

    public Long insertComment(CommentSaveRequest request) {
        Member findMember = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new BadRequestException(MEMBER_NOT_FOUND));
//        validateMemberIsCrew(findMember);
        Studylog findStudylog = studylogRepository.findById(request.getStudylogId())
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));

        Comment comment = request.toEntity(findMember, findStudylog);

        return commentRepository.save(comment).getId();
    }

    private void validateMemberIsCrew(final Member member) {
        if (member.hasLowerImportanceRoleThan(Role.CREW)) {
            throw new BadRequestException(MEMBER_NOT_ALLOWED);
        }
    }

    @Transactional(readOnly = true)
    public CommentsResponse findComments(Long studylogId) {
        Studylog findStudylog = studylogRepository.findById(studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));

        List<CommentResponse> commentResponses = commentRepository.findCommentByStudylog(
                findStudylog)
            .stream()
            .map(CommentResponse::of)
            .collect(Collectors.toList());

        return new CommentsResponse(commentResponses);
    }

    public Long updateComment(CommentUpdateRequest request) {
        validateExistsMember(request.getMemberId());
        validateExistsStudylog(request.getStudylogId());

        Comment comment = commentRepository.findById(request.getCommentId())
            .orElseThrow(() -> new BadRequestException(COMMENT_NOT_FOUND));
        comment.updateContent(request.getContent());

        return comment.getId();
    }

    public void deleteComment(Long memberId, Long studylogId, Long commentId) {
        validateExistsMember(memberId);
        validateExistsStudylog(studylogId);

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new BadRequestException(COMMENT_NOT_FOUND));

        comment.delete();
    }

    private void validateExistsMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BadRequestException(MEMBER_NOT_FOUND);
        }
    }

    private void validateExistsStudylog(Long studylogId) {
        if (!studylogRepository.existsById(studylogId)) {
            throw new BadRequestException(STUDYLOG_NOT_FOUND);
        }
    }
}
