package wooteco.prolog.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.comment.domain.CommentType.STUDY_LOG;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.comment.application.dto.CommentDeleteRequest;
import wooteco.prolog.comment.application.dto.CommentSaveRequest;
import wooteco.prolog.comment.application.dto.CommentSearchRequest;
import wooteco.prolog.comment.application.dto.CommentUpdateRequest;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.repository.CommentRepository;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StudylogRepository studyLogRepository;
    @Autowired
    private LevelLogRepository levelLogRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Map<String, Member> members = new HashMap<>();
    private Map<String, Studylog> studyLogs = new HashMap<>();
    private Map<String, LevelLog> levelLogs = new HashMap<>();

    @BeforeEach
    void beforeSetUp() {
        members.put("루키",
            memberRepository.save(new Member("wishoon", "루키", Role.CREW, 1L, "https://image.url")));
        studyLogs.put("루키의 학습로그", studyLogRepository.save(new Studylog(
            members.get("루키"),
            "체스 title",
            "체스 content",
            null,
            null,
            Collections.emptyList())));
        levelLogs.put("루키의 레벨로그", levelLogRepository.save(new LevelLog(
            "체스 title",
            "체스 content",
            members.get("루키"))));
    }

    @AfterEach
    void afterSetUp() {
        members.clear();
        studyLogs.clear();
        levelLogs.clear();
    }

    @Test
    @DisplayName("작성된 글 ID와 회원 ID를 통해서 댓글을 등록할 수 있다.")
    void create() {
        // given
        CommentSaveRequest request = new CommentSaveRequest(
            studyLogs.get("루키의 학습로그").getId(), members.get("루키").getId(), "댓글의 내용", STUDY_LOG);

        // when
        Long extract = commentService.insertComment(request);

        // then
        assertThat(extract).isNotNull();
    }

    @Test
    @DisplayName("작성된 글 ID를 기준으로 작성된 댓글들을 조회할 수 있다.")
    void findComments() {
        // given
        for (int i = 0; i < 5; i++) {
            commentService.insertComment(new CommentSaveRequest(
                studyLogs.get("루키의 학습로그").getId(), members.get("루키").getId(), "댓글의 내용", STUDY_LOG));
        }

        // when
        CommentsResponse comments = commentService.findComments(
            new CommentSearchRequest(1L, STUDY_LOG));

        // then
        assertThat(comments.getData()).hasSize(5);
    }

    @Test
    @DisplayName("작성된 댓글의 내용을 수정할 수 있다.")
    void updateComment() {
        // given
        CommentSaveRequest request = new CommentSaveRequest(
            studyLogs.get("루키의 학습로그").getId(), members.get("루키").getId(), "댓글의 내용", STUDY_LOG);
        Long commentId = commentService.insertComment(request);

        // when
        commentService.updateComment(new CommentUpdateRequest(
            members.get("루키").getId(),
            commentId,
            "변경될 댓글의 내용"));

        // then
        Comment extract = commentRepository.findById(commentId).get();
        assertThat(extract.getContent()).isEqualTo("변경될 댓글의 내용");
    }

    @Test
    @DisplayName("작성된 댓글을 삭제할 수 있다.")
    void deleteComment() {
        // given
        Long commentId = commentService.insertComment(new CommentSaveRequest(
            studyLogs.get("루키의 학습로그").getId(), members.get("루키").getId(), "댓글의 내용", STUDY_LOG));

        // when
        commentService.deleteComment(new CommentDeleteRequest(
            members.get("루키").getId(),
            commentId));

        // then
        Optional<Comment> extract = commentRepository.findByCommentId(commentId);
        assertThat(extract).isNotPresent();
    }
}
