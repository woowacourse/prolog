package wooteco.prolog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.util.Lists;
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
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class CommentRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MissionRepository missionRepository;

    private Member 루키;
    private Member 잉;
    private Session session_백엔드_레벨1;
    private Mission mission_백엔드_체스;
    private Studylog 루키_스터디로그;

    @BeforeEach
    void setUp() {
        루키 = memberRepository.save(new Member("wishoon", "루키", Role.CREW, 1L, "https://image.url"));
        잉 = memberRepository.save(new Member("iilo", "잉", Role.CREW, 2L, "https://image.url"));
        session_백엔드_레벨1 = sessionRepository.save(new Session("백엔드Java 레벨1"));
        mission_백엔드_체스 = missionRepository.save(new Mission("체스미션", session_백엔드_레벨1));
        루키_스터디로그 = studylogRepository.save(
            new Studylog(루키, "제목", "내용", session_백엔드_레벨1, mission_백엔드_체스, Lists.emptyList()));
    }

    @Test
    @DisplayName("스터디로그에 등록된 댓글 리스트를 조회할 수 있다.")
    void findCommentByStudylog() {
        // given
        commentRepository.save(new Comment(null, 루키, 루키_스터디로그, "루키 스터디로그의 내용"));
        commentRepository.save(new Comment(null, 잉, 루키_스터디로그, "루키 스터디로그의 내용"));

        // when
        List<Comment> findComments = commentRepository.findCommentByStudylog(루키_스터디로그);

        // then
        assertThat(findComments).hasSize(2);
    }

    @Test
    @DisplayName("스터디로그에 등록된 댓글 리스트 중 isDelete가 false인 댓글만 조회할 수 있다.")
    void findCommentOfIsDeleteFalseByStudylog() {
        // given
        Comment comment = commentRepository.save(
            new Comment(null, 루키, 루키_스터디로그, "루키 스터디로그의 내용"));
        commentRepository.save(new Comment(null, 잉, 루키_스터디로그, "루키 스터디로그의 내용"));

        // when
        comment.delete();
        entityManager.flush();
        List<Comment> findComments = commentRepository.findCommentByStudylog(루키_스터디로그);

        // then
        assertThat(findComments).hasSize(1);
    }
}
