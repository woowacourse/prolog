package wooteco.prolog.comment.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.comment.application.dto.CommentStudylogSaveRequest;
import wooteco.prolog.comment.application.dto.CommentStudylogUpdateRequest;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.domain.CommentStudylog;
import wooteco.prolog.comment.domain.repository.CommentStudylogRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class CommentStudylogServiceTest {

    @Autowired
    private CommentStudylogService commentStudylogService;
    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private CommentStudylogRepository commentStudylogRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Map<String, Object> data = new HashMap<>();

    @BeforeEach
    void beforeSetUp() {
        data.put("루키",
            memberRepository.save(new Member("wishoon", "루키", Role.CREW, 1L, "https://image.url")));
        data.put("수달",
            memberRepository.save(new Member("her0807", "수달", Role.CREW, 2L, "https://image.url")));
        data.put("루키의 학습로그", studylogRepository.save(new Studylog(
            (Member) data.get("루키"),
            "체스 title",
            "체스 content",
            null,
            null,
            Collections.emptyList())));
    }

    @AfterEach
    void afterSetUp() {
        data.clear();
    }

    @Test
    @DisplayName("스터디로그 ID와 회원 ID를 통해서 학습로그 댓글을 등록할 수 있다.")
    void create() {
        // given
        Member member = (Member) data.get("루키");
        Studylog studylog = (Studylog) data.get("루키의 학습로그");
        CommentStudylogSaveRequest request = new CommentStudylogSaveRequest(
            member.getId(), studylog.getId(), "댓글 내용");

        // when
        Long commentId = commentStudylogService.insertComment(request);

        // then
        assertThat(commentId).isNotNull();
    }

    @Test
    @DisplayName("스터디로그 ID를 통해서 등록된 댓글 목록을 조회할 수 있다.")
    void findCommentsByStudylogId() {
        // given
        Member rookie = (Member) data.get("루키");
        Member sudal = (Member) data.get("수달");
        Studylog studylog = (Studylog) data.get("루키의 학습로그");
        commentStudylogService.insertComment(
            new CommentStudylogSaveRequest(rookie.getId(), studylog.getId(), "댓글 내용"));
        commentStudylogService.insertComment(
            new CommentStudylogSaveRequest(sudal.getId(), studylog.getId(), "댓글 내용"));

        // when
        CommentsResponse commentsResponse = commentStudylogService.findComments(studylog.getId());

        // then
        assertThat(commentsResponse.getData()).hasSize(2);
    }

    @Test
    @DisplayName("스터디로그 댓글 ID를 통해서 등록된 댓글을 수정할 수 있다.")
    void updateCommentByStudylogCommentId() {
        // given
        Member rookie = (Member) data.get("루키");
        Member sudal = (Member) data.get("수달");
        Studylog studylog = (Studylog) data.get("루키의 학습로그");
        commentStudylogService.insertComment(
            new CommentStudylogSaveRequest(rookie.getId(), studylog.getId(), "댓글 내용"));
        commentStudylogService.insertComment(
            new CommentStudylogSaveRequest(sudal.getId(), studylog.getId(), "댓글 내용"));

        // when
        commentStudylogService.updateComment(new CommentStudylogUpdateRequest(
            rookie.getId(), studylog.getId(), 1L, "댓글 내용에 대한 변경"));
        commentStudylogRepository.flush();

        // then
        CommentStudylog extract = commentStudylogRepository.findByCommentId(1L).get();
        assertThat(extract.getComment().getContent()).isEqualTo("댓글 내용에 대한 변경");
    }

    @Test
    @DisplayName("스터디로그 ID를 통해서 등록된 댓글의 표시 여부를 보이지 않게 할 수 있다.")
    void deleteCommentByStudylogCommentId() {
        // given
        Member rookie = (Member) data.get("루키");
        Member sudal = (Member) data.get("수달");
        Studylog studylog = (Studylog) data.get("루키의 학습로그");
        commentStudylogService.insertComment(
            new CommentStudylogSaveRequest(rookie.getId(), studylog.getId(), "댓글 내용"));
        commentStudylogService.insertComment(
            new CommentStudylogSaveRequest(sudal.getId(), studylog.getId(), "댓글 내용"));

        // when
        commentStudylogService.deleteComment(1L, 1L, 1L);
        commentStudylogRepository.flush();

        // then
        Optional<CommentStudylog> extract = commentStudylogRepository.findByCommentId(1L);
        assertThat(extract).isNotPresent();
    }
}
