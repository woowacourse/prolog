package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.common.exception.BadRequestCode.COMMENT_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.CommentMemberResponse;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentSaveRequest;
import wooteco.prolog.studylog.application.dto.CommentUpdateRequest;
import wooteco.prolog.studylog.application.dto.CommentsResponse;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.CommentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private StudylogRepository studylogRepository;
    @InjectMocks
    private CommentService commentService;

    @DisplayName("댓글을 저장한뒤 저장된 댓글의 ID를 반환한다.")
    @Test
    void insertComment_success() {
        //given
        final Member 멤버 = new Member("username", "nickname", Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 학습로그 = new Studylog(멤버, "title", "content", 자동차_미션, 자바_스프링_태그_목록);
        final Comment 댓글 = new Comment(1L, 멤버, 학습로그, "content");
        final CommentSaveRequest 댓글_저장_요청 = new CommentSaveRequest(1L, 1L, "content");

        when(memberRepository.findById(any())).thenReturn(Optional.of(멤버));
        when(studylogRepository.findById(any())).thenReturn(Optional.of(학습로그));
        when(commentRepository.save(any())).thenReturn(댓글);

        //when
        final Long savedCommentId = commentService.insertComment(댓글_저장_요청);

        //then
        verify(commentRepository).save(any());
        assertThat(savedCommentId).isEqualTo(1L);
    }

    @DisplayName("댓글을 저장할 때 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void insertComment_fail_memberNotExist() {
        //given
        final CommentSaveRequest 댓글_저장_요청 = new CommentSaveRequest(1L, 1L, "content");
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> commentService.insertComment(댓글_저장_요청))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(MEMBER_NOT_FOUND.getMessage());
    }

    @DisplayName("댓글을 저장할 때 스터디 로그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void insertComment_fail_studylogNotExist() {
        //given
        final Member 멤버 = new Member("username", "nickname", Role.CREW, 1L, "imageUrl");
        final CommentSaveRequest 댓글_저장_요청 = new CommentSaveRequest(1L, 1L, "content");

        when(memberRepository.findById(any())).thenReturn(Optional.of(멤버));
        when(studylogRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> commentService.insertComment(댓글_저장_요청))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
    }

    @DisplayName("특정 스터디 로그의 모든 댓글을 조회한다.")
    @Test
    void findComments_success() {
        //given
        final Member 멤버 = new Member(1L, "username", "nickname", Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 학습로그 = new Studylog(멤버, "title", "content", 자동차_미션, 자바_스프링_태그_목록);
        final Comment 첫번쨰_댓글 = new Comment(1L, 멤버, 학습로그, "content1");
        final Comment 두번쨰_댓글 = new Comment(2L, 멤버, 학습로그, "content2");

        when(studylogRepository.findById(any())).thenReturn(Optional.of(학습로그));
        when(commentRepository.findCommentByStudylog(any())).thenReturn(
            Arrays.asList(첫번쨰_댓글, 두번쨰_댓글));

        //when
        final CommentsResponse comments = commentService.findComments(1L);

        //then
        final CommentResponse 첫번쨰_댓글_응답 = comments.getData().get(0);
        final CommentResponse 두번쨰_댓글_응답 = comments.getData().get(1);

        Assertions.assertAll(
            () -> assertThat(comments.getData()).hasSize(2),
            () -> assertThat(첫번쨰_댓글_응답.getContent()).isEqualTo("content1"),
            () -> assertThat(첫번쨰_댓글_응답.getId()).isEqualTo(1L),
            () -> assertThat(첫번쨰_댓글_응답.getAuthor()).usingRecursiveComparison()
                .isEqualTo(
                    new CommentMemberResponse(1L, "username", "nickname", "imageUrl", "CREW")),
            () -> assertThat(두번쨰_댓글_응답.getContent()).isEqualTo("content2"),
            () -> assertThat(두번쨰_댓글_응답.getId()).isEqualTo(2L),
            () -> assertThat(두번쨰_댓글_응답.getAuthor()).usingRecursiveComparison()
                .isEqualTo(
                    new CommentMemberResponse(1L, "username", "nickname", "imageUrl", "CREW"))
        );
    }

    @DisplayName("댓글을 찾으려는 스터디 로그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void findComments_fail_studylogNotExist() {
        //given
        when(studylogRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> commentService.findComments(1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
    }

    @DisplayName("특정 학습로그의 특정 멤버의 댓글이 수정될 시 댓글의 아이디를 반환한다.")
    @Test
    void updateComment_success() {
        //given
        final Member 멤버 = new Member(1L, "username", "nickname", Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 학습로그 = new Studylog(멤버, "title", "content", 자동차_미션, 자바_스프링_태그_목록);
        final Comment 댓글 = new Comment(1L, 멤버, 학습로그, "content");

        final CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, 1L,
            1L, "content");

        when(memberRepository.existsById(any())).thenReturn(true);
        when(studylogRepository.existsById(any())).thenReturn(true);
        when(commentRepository.findById(any())).thenReturn(Optional.of(댓글));

        //when
        final Long commentId = commentService.updateComment(commentUpdateRequest);

        //then
        assertThat(commentId).isEqualTo(1L);
    }

    @DisplayName("수정하려는 댓글의 멤버가 존재하지 않으면 예외가 발생한다.")
    @Test
    void updateComment_fail_memberNotExist() {
        //given
        when(memberRepository.existsById(any())).thenReturn(false);

        final CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, 1L,
            1L, "content");

        //when
        //then
        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(MEMBER_NOT_FOUND.getMessage());
    }

    @DisplayName("수정하려는 댓글의 학습로그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void updateComment_fail_studylogNotExist() {
        //given
        when(memberRepository.existsById(any())).thenReturn(true);
        when(studylogRepository.existsById(any())).thenReturn(false);

        final CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, 1L,
            1L, "content");

        //when
        //then
        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
    }

    @DisplayName("수정하려는 댓글이 존재하지 않으면 예외가 발생한다.")
    @Test
    void updateComment_fail_commentNotExist() {
        //given
        when(memberRepository.existsById(any())).thenReturn(true);
        when(studylogRepository.existsById(any())).thenReturn(true);
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        final CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest(1L, 1L,
            1L, "content");

        //when
        //then
        assertThatThrownBy(() -> commentService.updateComment(commentUpdateRequest))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(COMMENT_NOT_FOUND.getMessage());
    }

    @DisplayName("특정 학습로그의 특정 멤버의 댓글을 삭제시 댓글의 삭제 상태를 true로 업데이트한다.")
    @Test
    void deleteComment_success() {
        //given
        final Member 멤버 = new Member(1L, "username", "nickname", Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 학습로그 = new Studylog(멤버, "title", "content", 자동차_미션, 자바_스프링_태그_목록);
        final Comment 댓글 = new Comment(1L, 멤버, 학습로그, "content");

        when(memberRepository.existsById(any())).thenReturn(true);
        when(studylogRepository.existsById(any())).thenReturn(true);
        when(commentRepository.findById(any())).thenReturn(Optional.of(댓글));

        //when
        //then
        commentService.deleteComment(1L, 1L, 1L);
        assertThat(댓글.isDelete()).isTrue();
    }

    @DisplayName("삭제하려는 댓글의 멤버가 존재하지 않으면 예외가 발생한다.")
    @Test
    void deleteComment_fail_memberNotExist() {
        //given
        when(memberRepository.existsById(any())).thenReturn(false);

        //when
        //then
        assertThatThrownBy(() -> commentService.deleteComment(1L, 1L, 1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(MEMBER_NOT_FOUND.getMessage());
    }

    @DisplayName("삭제하려는 댓글의 학습로그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void deleteComment_fail_studylogNotExist() {
        //given
        when(memberRepository.existsById(any())).thenReturn(true);
        when(studylogRepository.existsById(any())).thenReturn(false);

        //when
        //then
        assertThatThrownBy(() -> commentService.deleteComment(1L, 1L, 1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
    }

    @DisplayName("삭제하려는 댓글이 존재하지 않으면 예외가 발생한다.")
    @Test
    void deleteComment_fail_commentNotExist() {
        //given
        when(memberRepository.existsById(any())).thenReturn(true);
        when(studylogRepository.existsById(any())).thenReturn(true);
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> commentService.deleteComment(1L, 1L, 1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(COMMENT_NOT_FOUND.getMessage());
    }
}
