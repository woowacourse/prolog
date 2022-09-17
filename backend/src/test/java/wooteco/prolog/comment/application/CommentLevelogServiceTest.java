package wooteco.prolog.comment.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.comment.application.dto.CommentLevellogSaveRequest;
import wooteco.prolog.comment.application.dto.CommentLevellogUpdateReqeust;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.domain.CommentLevellog;
import wooteco.prolog.comment.domain.repository.CommentLevellogRepository;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class CommentLevelogServiceTest {

    @Autowired
    private CommentLevellogService commentLevellogService;
    @Autowired
    private LevelLogRepository levelLogRepository;
    @Autowired
    private CommentLevellogRepository commentLevellogRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Map<String, Object> data = new HashMap<>();

    @BeforeEach
    void beforeSetUp() {
        data.put("루키",
            memberRepository.save(new Member("wishoon", "루키", Role.CREW, 1L, "https://image.url")));
        data.put("수달",
            memberRepository.save(new Member("her0807", "수달", Role.CREW, 2L, "https://image.url")));
        data.put("루키의 레벨로그", levelLogRepository.save(new LevelLog(
            "체스 title",
            "체스 content",
            (Member) data.get("루키"))));
    }

    @AfterEach
    void afterSetUp() {
        data.clear();
    }

    @Test
    @DisplayName("레벨로그 ID와 회원 ID를 통해서 레벨로그 댓글을 등록할 수 있다.")
    void create() {
        // given
        Member member = (Member) data.get("루키");
        LevelLog levelLog = (LevelLog) data.get("루키의 레벨로그");
        CommentLevellogSaveRequest request = new CommentLevellogSaveRequest(
            member.getId(), levelLog.getId(), "댓글 내용");

        // when
        Long commentId = commentLevellogService.insertComment(request);

        // then
        assertThat(commentId).isNotNull();
    }

    @Test
    @DisplayName("레벨로그 ID를 통해서 등록된 댓글 목록을 조회할 수 있다.")
    void findCommentsByLevellogId() {
        // given
        Member rookie = (Member) data.get("루키");
        Member sudal = (Member) data.get("수달");
        LevelLog levelLog = (LevelLog) data.get("루키의 레벨로그");
        commentLevellogService.insertComment(new CommentLevellogSaveRequest(
            rookie.getId(), levelLog.getId(), "루키의 댓글 내용"));
        commentLevellogService.insertComment(new CommentLevellogSaveRequest(
            sudal.getId(), levelLog.getId(), "수달의 댓글 내용"));

        // when
        CommentsResponse commentsResponse = commentLevellogService.findComments(levelLog.getId());

        // then
        assertThat(commentsResponse.getData()).hasSize(2);
    }

    @Test
    @DisplayName("레벨로그 ID를 통해서 등록된 댓글을 수정할 수 있다.")
    void updateCommentByLevellogCommentId() {
        // given
        Member rookie = (Member) data.get("루키");
        Member sudal = (Member) data.get("수달");
        LevelLog levelLog = (LevelLog) data.get("루키의 레벨로그");
        commentLevellogService.insertComment(
            new CommentLevellogSaveRequest(rookie.getId(), levelLog.getId(), "루키의 댓글 내용"));
        commentLevellogService.insertComment(
            new CommentLevellogSaveRequest(sudal.getId(), levelLog.getId(), "수달의 댓글 내용"));

        // when
        commentLevellogService.updateComment(new CommentLevellogUpdateReqeust(
            rookie.getId(), levelLog.getId(), 1L, "댓글 내용에 대한 변경"));
        commentLevellogRepository.flush();

        // then
        CommentLevellog extract = commentLevellogRepository.findByCommentId(1L).get();
        assertThat(extract.getComment().getContent()).isEqualTo("댓글 내용에 대한 변경");
    }

    @Test
    @DisplayName("레벨로그 ID를 통해서 등록된 댓글의 표시 여부를 보이지 않게 할 수 있다.")
    void deleteCommentByLevellogCommentId() {
        // given
        Member rookie = (Member) data.get("루키");
        Member sudal = (Member) data.get("수달");
        LevelLog levelLog = (LevelLog) data.get("루키의 레벨로그");
        commentLevellogService.insertComment(
            new CommentLevellogSaveRequest(rookie.getId(), levelLog.getId(), "루키의 댓글 내용"));
        commentLevellogService.insertComment(
            new CommentLevellogSaveRequest(sudal.getId(), levelLog.getId(), "수달의 댓글 내용"));

        // when
        commentLevellogService.deleteComment(1L, 1L, 1L);
        commentLevellogRepository.flush();

        // then
        Optional<CommentLevellog> extract = commentLevellogRepository.findByCommentId(1L);
        assertThat(extract).isNotPresent();
    }
}
