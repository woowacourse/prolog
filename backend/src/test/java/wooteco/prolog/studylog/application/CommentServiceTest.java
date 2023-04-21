package wooteco.prolog.studylog.application;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


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

    @Test
    public void 존재하지_않는_멤버일_경우_댓글_수정시_MemberNotFoundException이_발생한다() {
        CommentService commentService = new CommentService(commentRepository, memberRepository, studylogRepository);

        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(-1L, 1L, 1L, "테스트 문장입니다.");

        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest)).isInstanceOf(MemberNotFoundException.class);

    }

    @Test
    public void 존재하지_않는_학습로그일_경우_댓글_수정시_StudylogNotFoundException이_발생한다() {
        CommentService commentService = new CommentService(commentRepository, memberRepository, studylogRepository);

        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, -1L, 1L, "테스트 문장입니다.");

        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest)).isInstanceOf(StudylogNotFoundException.class);

    }

    @Test
    public void 회원과_학습로그_모두_존재할_경우_댓글이_정상적으로_수정된다() {
        Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
        Session session = new Session("백엔드 레벨 1 자바");
        Mission mission = new Mission("백엔드 체스", session);
        Studylog studylog = new Studylog(member, "제목", "내용", session, mission, Lists.emptyList());

        Comment comment = new Comment(1L, member, studylog, "테스트 문장입니다.");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentService commentService = new CommentService(commentRepository, memberRepository, studylogRepository);

        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, 1L, 1L, "테스트 문장입니다.");

        assertThat(commentService.updateComment(commentUpdateRequest)).isNotNull();

    }


}
