package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import org.elasticsearch.common.collect.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.application.dto.CommentMemberResponse;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentSaveRequest;
import wooteco.prolog.studylog.application.dto.CommentsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private StudylogService studylogService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Member 브라운;
    private Member 루키;
    private Member 잉;

    private Session session_백엔드_레벨1;
    private Studylog 체스_스터디로그;
    private Studylog null_스터디로그;

    @BeforeEach
    void setUp() {
        브라운 = memberRepository.save(new Member("brown", "브라운", Role.CREW, 1L, "imageUrl"));
        루키 = memberRepository.save(new Member("rookie", "루키", Role.CREW, 2L, "imageUrl"));
        잉 = memberRepository.save(new Member(" ", "잉", Role.CREW, 3L, "imageUrl"));

        session_백엔드_레벨1 = sessionRepository.save(new Session("백엔드Java 레벨1"));
        Mission mission_체스미션 = missionRepository.save(new Mission("체스미션", session_백엔드_레벨1));

        체스_스터디로그 = studylogService.save(
            new Studylog(
                브라운,
                "체스 title",
                "체스 content",
                session_백엔드_레벨1,
                mission_체스미션,
                Collections.emptyList()));

        null_스터디로그 = studylogService.save(
            new Studylog(
                루키,
                "null title",
                "null content",
                null,
                null,
                Collections.emptyList()));
    }

    @Test
    @DisplayName("스터디로그 ID와 Member ID를 통해서 댓글을 등록한다.")
    void create() {
        // given
        CommentSaveRequest request = new CommentSaveRequest(루키.getId(), 체스_스터디로그.getId(), "댓글의 내용");

        // when
        Long commentId = commentService.insertComment(request);

        // then
        assertThat(commentId).isNotNull();
    }

    @Test
    @DisplayName("댓글 등록 시 댓글 내용이 빈값일 경우 예외가 발생한다.")
    void create_nullContentException() {
        // given
        CommentSaveRequest request = new CommentSaveRequest(루키.getId(), 체스_스터디로그.getId(), null);

        // when & then
        assertThatThrownBy(() -> commentService.insertComment(request))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("스터디로그 ID를 통해서 등록된 댓글 목록을 조회한다.")
    void findComments() {
        // given
        CommentSaveRequest 루키_요청 = new CommentSaveRequest(루키.getId(), 체스_스터디로그.getId(), "댓글의 내용");
        commentService.insertComment(루키_요청);
        CommentSaveRequest 잉_요청 = new CommentSaveRequest(잉.getId(), 체스_스터디로그.getId(), "댓글의 내용");
        commentService.insertComment(잉_요청);

        // when
        CommentsResponse commentsResponse = commentService.findComments(체스_스터디로그.getId());

        // then
        assertThat(commentsResponse.getData()).usingRecursiveComparison().ignoringFields("id", "createAt").isEqualTo(List.of(
            new CommentResponse(null, new CommentMemberResponse(루키.getId(), 루키.getUsername(), 루키.getNickname(), "imageUrl", 루키.getRole().name()), "댓글의 내용", null),
            new CommentResponse(null, new CommentMemberResponse(잉.getId(), 잉.getUsername(), 잉.getNickname(), "imageUrl", 잉.getRole().name()), "댓글의 내용", null)
        ));
    }
}
