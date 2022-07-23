package wooteco.prolog.studylog.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentSaveRequest;
import wooteco.prolog.studylog.application.dto.CommentsResponse;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.CommentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Transactional
@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final StudylogRepository studylogRepository;

    public Long insertComment(CommentSaveRequest request) {
        Member findMember = memberRepository.findById(request.getMemberId())
            .orElseThrow(MemberNotFoundException::new);
        Studylog findStudylog = studylogRepository.findById(request.getStudylogId())
                .orElseThrow(StudylogNotFoundException::new);

        Comment comment = request.toEntity(findMember, findStudylog);

        return commentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse findComments(Long studylogId) {
        Studylog findStudylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);

        List<CommentResponse> commentResponses = commentRepository.findCommentByStudylog(findStudylog)
            .stream()
            .map(CommentResponse::of)
            .collect(Collectors.toList());

        return new CommentsResponse(commentResponses);
    }
}
