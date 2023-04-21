package wooteco.prolog.studylog.application;

import org.assertj.core.util.Lists;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class StudylogLikeServiceTest {
    @Nested
    public class 좋아요_등록 {
        @Test
        public void 좋아요가_정상적으로_추가된다() {
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);

            Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
            Session session = new Session("백엔드 레벨 1 자바");
            Mission mission = new Mission("백엔드 체스", session);
            Studylog studylog = new Studylog(member, "제목", "내용", session, mission, Lists.emptyList());

            when(studylogRepository.findById(1L)).thenReturn(Optional.of(studylog));

            when(memberService.findById(1L)).thenReturn(member);
            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository, memberService);

            assertThat(studylogLikeService.likeStudylog(1L, 1L, true)).isNotNull();
        }

        @Test
        public void 멤버가_유효하지_않을_때_InvalidLikeRequestException이_발생한다() {
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);

            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository, memberService);

            assertThatThrownBy(
                () -> studylogLikeService.likeStudylog(1L, 1L, false)
            ).isInstanceOf(InvalidLikeRequestException.class);
        }
    }

    @Nested
    public class 좋아요_취소 {
        @Test
        public void 좋아요가_추가된_상태가_아니라면_InvalidUnlikeRequestException이_발생한다() {
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);

            Member member = new Member("yboy", "잉", Role.CREW, 1L, "https://");
            Session session = new Session("백엔드 레벨 1 자바");
            Mission mission = new Mission("백엔드 체스", session);

            Studylog studylog = new Studylog(member, "제목", "내용", session, mission, Lists.emptyList());
            when(studylogRepository.findById(1L)).thenReturn(Optional.of(studylog));

            when(memberService.findById(1L)).thenReturn(member);
            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository, memberService);
            
            assertThatThrownBy(() -> studylogLikeService.unlikeStudylog(1L, 1L, true)).isInstanceOf(InvalidUnlikeRequestException.class);

        }

        @Test
        public void 멤버가_유효하지_않을_때_InvalidLikeRequestException이_발생한다() {
            StudylogRepository studylogRepository = Mockito.mock(StudylogRepository.class);
            MemberService memberService = Mockito.mock(MemberService.class);

            StudylogLikeService studylogLikeService = new StudylogLikeService(studylogRepository, memberService);

            assertThatThrownBy(
                () -> studylogLikeService.unlikeStudylog(1L, 1L, false)
            ).isInstanceOf(InvalidLikeRequestException.class);
        }
    }

}
