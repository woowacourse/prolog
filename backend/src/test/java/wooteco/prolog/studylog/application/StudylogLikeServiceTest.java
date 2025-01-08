package wooteco.prolog.studylog.application;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static wooteco.prolog.common.exception.BadRequestCode.INVALID_LIKE_REQUEST_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.INVALID_UNLIKE_REQUEST_EXCEPTION;

public class StudylogLikeServiceTest {

    private static final Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
    private static final Session session = new Session("백엔드 레벨 1 자바");
    private static final Mission mission = new Mission("백엔드 체스", session);
    private static final Studylog studylog = new Studylog(
        member, "제목", "내용", session, mission, Lists.emptyList());

    private StudylogRepository studylogRepository;
    private StudylogLikeService studylogLikeService;
    private MemberService memberService;

    @BeforeEach
    public void init() {
        studylogRepository = Mockito.mock(StudylogRepository.class);
        memberService = Mockito.mock(MemberService.class);
        studylogLikeService = new StudylogLikeService(studylogRepository,
            memberService);
    }

    @DisplayName("likeStudylog(Long memberId, Long studylogId, boolean isMember)가 호출될 때")
    @Nested
    public class likeStudylog {

        @DisplayName("멤버 아이디가 유효할 때 오류가 발생하지 않는다.")
        @Test
        public void success() {
            //given
            when(studylogRepository.findById(1L)).thenReturn(Optional.of(studylog));
            when(memberService.findById(1L)).thenReturn(member);

            //when, then
            assertThatCode(
                () -> studylogLikeService.likeStudylog(1L, 1L, true)).doesNotThrowAnyException();
        }

        @DisplayName("멤버_아이디가_유효하지_않을_때_InvalidLikeRequestException이_발생한다")
        @Test
        public void fail_because_member_not_valid() {
            //given, when, then
            assertThatThrownBy(
                () -> studylogLikeService.likeStudylog(1L, 1L, false))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_LIKE_REQUEST_EXCEPTION.getMessage());
        }
    }

    @DisplayName("unlikeStudylog(Long memberId, Long studylogId, boolean isMember)가 호출될 때")
    @Nested
    public class unlikeStudylog {

        @DisplayName("좋아요가 추가된 상태가 아니라면 InvalidUnlikeRequestException이 발생한다")
        @Test
        public void fail_because_like_not_exist() {
            //given
            when(studylogRepository.findById(1L)).thenReturn(Optional.of(studylog));
            when(memberService.findById(1L)).thenReturn(member);

            //when, then
            assertThatThrownBy(() -> studylogLikeService.unlikeStudylog(1L, 1L, true))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_UNLIKE_REQUEST_EXCEPTION.getMessage());
        }

        @DisplayName("멤버_아이디가_유효하지_않을_때_InvalidLikeRequestException이_발생한다")
        @Test
        public void fail_because_member_not_valid() {
            //given, when, then
            assertThatThrownBy(
                () -> studylogLikeService.unlikeStudylog(1L, 1L, false))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_LIKE_REQUEST_EXCEPTION.getMessage());
        }
    }

}
