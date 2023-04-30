package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.InvalidLikeRequestException;
import wooteco.prolog.studylog.exception.InvalidUnlikeRequestException;

public class StudylogLikeServiceTest {

    @DisplayName("likeStudylog(Long memberId, Long studylogId, boolean isMember)가 호출될 때")
    @Nested
    public class likeStudylog {

        @DisplayName("멤버 아이디가 유효할 때 오류가 발생하지 않는다.")
        @Test
        public void success() {
            //given
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);
            Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
            Session session = new Session("백엔드 레벨 1 자바");
            Mission mission = new Mission("백엔드 체스", session);
            Studylog studylog = new Studylog(member, "제목", "내용", session, mission,
                Lists.emptyList());
            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository,
                memberService);

            when(studylogRepository.findById(1L)).thenReturn(Optional.of(studylog));
            when(memberService.findById(1L)).thenReturn(member);

            //when, then
            assertThatCode(
                () -> studylogLikeService.likeStudylog(1L, 1L, true)).doesNotThrowAnyException();
        }

        @DisplayName("멤버_아이디가_유효하지_않을_때_InvalidLikeRequestException이_발생한다")
        @Test
        public void fail_because_member_not_valid() {
            //given
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);
            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository,
                memberService);

            //when, then
            assertThatThrownBy(
                () -> studylogLikeService.likeStudylog(1L, 1L, false)
            ).isInstanceOf(InvalidLikeRequestException.class);
        }
    }

    @DisplayName("unlikeStudylog(Long memberId, Long studylogId, boolean isMember)가 호출될 때")
    @Nested
    public class unlikeStudylog {

        @DisplayName("좋아요가 추가된 상태가 아니라면 InvalidUnlikeRequestException이 발생한다")
        @Test
        public void fail_because_like_not_exist() {
            //given
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);

            Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
            Session session = new Session("백엔드 레벨 1 자바");
            Mission mission = new Mission("백엔드 체스", session);
            Studylog studylog = new Studylog(member, "제목", "내용", session, mission,
                Lists.emptyList());
            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository,
                memberService);

            when(studylogRepository.findById(1L)).thenReturn(Optional.of(studylog));
            when(memberService.findById(1L)).thenReturn(member);

            //when, then
            assertThatThrownBy(() -> studylogLikeService.unlikeStudylog(1L, 1L, true)).isInstanceOf(
                InvalidUnlikeRequestException.class);

        }

        @DisplayName("멤버_아이디가_유효하지_않을_때_InvalidLikeRequestException이_발생한다")
        @Test
        public void fail_because_member_not_valid() {
            //given
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);
            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository,
                memberService);

            //when, then
            assertThatThrownBy(
                () -> studylogLikeService.unlikeStudylog(1L, 1L, false)
            ).isInstanceOf(InvalidLikeRequestException.class);
        }
    }

}
