package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.CommentUpdateRequest;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.CommentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;


public class CommentServiceTest {

    CommentRepository commentRepository;
    MemberRepository memberRepository;
    StudylogRepository studylogRepository;

    @BeforeEach
    public void init() {
        commentRepository = Mockito.mock(CommentRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        studylogRepository = Mockito.mock(StudylogRepository.class);

        when(memberRepository.existsById(1L)).thenReturn(true);
        when(studylogRepository.existsById(1L)).thenReturn(true);
    }

    @DisplayName("updateComment(CommentUpdateRequest request)의 인자에 들어가는 회원이 레파지토리에 존재하지 않을 경우 MemberNotFoundException이 발생한다")
    @Test
    public void updateComment_fail_because_member_not_exist() {
        //given
        CommentService commentService = new CommentService(commentRepository, memberRepository,
            studylogRepository);
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(-1L, 1L, 1L,
            "테스트 문장입니다.");

        //when, then
        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest)).isInstanceOf(
            MemberNotFoundException.class);

    }

    @DisplayName("updateComment(CommentUpdateRequest request)의 인자에 들어가는 학습로그가 레파지토리에 존재하지 않을 경우 StudylogNotFoundException이 발생한다")
    @Test
    public void updateComment_fail_because_studylog_not_exist() {
        //given
        CommentService commentService = new CommentService(commentRepository, memberRepository,
            studylogRepository);
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, -1L, 1L,
            "테스트 문장입니다.");

        //when, then
        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest)).isInstanceOf(
            StudylogNotFoundException.class);

    }

    @DisplayName("updateComment(CommentUpdateRequest request)의 인자에 들어가는 회원과 학습로그가 모두 레파지토리에 존재할 경우 댓글이 정상적으로 수정된다")
    @Test
    public void updateComment_success() {
        //given
        Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
        Session session = new Session("백엔드 레벨 1 자바");
        Mission mission = new Mission("백엔드 체스", session);
        Studylog studylog = new Studylog(member, "제목", "내용", session, mission, Lists.emptyList());
        Comment comment = new Comment(1L, member, studylog, "테스트 문장입니다.");
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, 1L, 1L,
            "테스트 문장입니다.");
        CommentService commentService = new CommentService(commentRepository, memberRepository,
            studylogRepository);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        //when, then
        assertThat(commentService.updateComment(commentUpdateRequest)).isNotNull();
    }
}
