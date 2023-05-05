package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.exception.StudylogScrapAlreadyRegisteredException;

@ExtendWith(MockitoExtension.class)
class StudylogScrapServiceTest {

    private static final Session TEST_SESSION = new Session(4L, 5L, "세션");
    private static final Mission TEST_MISSION = new Mission(6L, "레벨 2 - 웹 자동차 경주", TEST_SESSION);
    private static final Member TEST_MEMBER_CREW1 = new Member(1L, "홍혁준", "홍실",
        Role.CREW, 2L, null, null);
    private static final Member TEST_MEMBER_CREW2 = new Member(2L, "송세연", "아마란스",
        Role.CREW, 2L, null, null);
    private static final Studylog TEST_STUDYLOG1 = new Studylog(TEST_MEMBER_CREW1, "레벨 1 레벨인터뷰", "레벨인터뷰에 대한 내용입니다."
        , TEST_MISSION, Collections.emptyList());
    private static final Studylog TEST_STUDYLOG2 = new Studylog(TEST_MEMBER_CREW2, "레벨 1 레벨인터뷰", "레벨인터뷰에 대한 내용입니다."
        , TEST_MISSION, Collections.emptyList());
    private static final StudylogScrap STUDYLOG_SCRAP1 = new StudylogScrap(1L, TEST_MEMBER_CREW1, TEST_STUDYLOG1);
    private static final StudylogScrap STUDYLOG_SCRAP2 = new StudylogScrap(2L, TEST_MEMBER_CREW2, TEST_STUDYLOG2);

    @Mock
    private StudylogScrapRepository studylogScrapRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private StudylogRepository studylogRepository;
    @InjectMocks
    private StudylogScrapService studylogScrapService;

    @DisplayName("regeisterScrap이 호출되면,")
    @Nested
    class registerScrap {

        @DisplayName("등록하려는 스크랩이 이미 등록된 경우, Exception을 throw한다.")
        @Test
        void fail_already_register() {
            //given
            doReturn(1).when(studylogScrapRepository).countByMemberIdAndScrapStudylogId(anyLong(), anyLong());

            //when,then
            assertThatThrownBy(() -> studylogScrapService.registerScrap(1L, 2L)).isInstanceOf(
                StudylogScrapAlreadyRegisteredException.class);
        }

        @DisplayName("값이 유효한 경우 정상적으로 스크랩이 등록된다.")
        @Test
        void success() {
            //given
            doReturn(0).when(studylogScrapRepository).countByMemberIdAndScrapStudylogId(anyLong(), anyLong());
            doReturn(Optional.of(TEST_STUDYLOG1)).when(studylogRepository).findById(anyLong());
            doReturn(Optional.of(TEST_MEMBER_CREW1)).when(memberRepository).findById(anyLong());

            //when
            MemberScrapResponse memberScrapResponse = studylogScrapService.registerScrap(1L, 1L);

            //then
            assertAll(() -> verify(studylogScrapRepository).save(any(StudylogScrap.class)),
                () -> assertThat(memberScrapResponse.getMemberResponse().getId()).isEqualTo(TEST_MEMBER_CREW1.getId()),
                () -> assertThat(memberScrapResponse.getStudylogResponse().getContent()).isEqualTo(
                    TEST_STUDYLOG1.getContent()));
        }
    }

    @DisplayName("스크랩을 제거하는 기능 테스트")
    @Test
    void unregisterScrapTest() {
        //given
        StudylogScrap studylogScrap = new StudylogScrap(1L, TEST_MEMBER_CREW1, TEST_STUDYLOG1);

        doReturn(Optional.of(studylogScrap)).when(studylogScrapRepository)
            .findByMemberIdAndStudylogId(anyLong(), anyLong());

        //when
        studylogScrapService.unregisterScrap(1L, 1L);

        //then
        verify(studylogScrapRepository).delete(studylogScrap);
    }

    @DisplayName("스크랩을 보여주는 기능 테스트")
    @Test
    void showScrapTest() {
        //given
        List<StudylogScrap> studylogScraps = Arrays.asList(STUDYLOG_SCRAP1, STUDYLOG_SCRAP2);
        Page<StudylogScrap> studylogScrapPages = new PageImpl<>(studylogScraps);
        Pageable pageable = mock(Pageable.class);

        doReturn(studylogScrapPages).when(studylogScrapRepository).findByMemberId(anyLong(), any(Pageable.class));

        //when
        StudylogsResponse studylogsResponse = studylogScrapService.showScrap(1L, pageable);

        //then
        assertThat(studylogsResponse.getData()).extracting(StudylogResponse::getAuthor)
            .extracting(MemberResponse::getId)
            .contains(STUDYLOG_SCRAP1.getMember().getId(), STUDYLOG_SCRAP2.getMember().getId());
    }
}
