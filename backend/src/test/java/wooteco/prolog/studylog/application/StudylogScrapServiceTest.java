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

    @Nested
    class 스크랩을_등록하는_기능 {

        @Test
        void 등록하려는_스크랩이_이미_등록되어있는_경우_Exception_thorw하는지_테스트() {
            doReturn(1).when(studylogScrapRepository).countByMemberIdAndScrapStudylogId(anyLong(), anyLong());

            assertThatThrownBy(() -> studylogScrapService.registerScrap(1L, 2L)).isInstanceOf(
                StudylogScrapAlreadyRegisteredException.class);
        }

        @Test
        void 정상적으로_스크랩을_등록하는_기능_테스트() {
            doReturn(0).when(studylogScrapRepository).countByMemberIdAndScrapStudylogId(anyLong(), anyLong());
            doReturn(Optional.of(TEST_STUDYLOG1)).when(studylogRepository).findById(anyLong());
            doReturn(Optional.of(TEST_MEMBER_CREW1)).when(memberRepository).findById(anyLong());

            MemberScrapResponse memberScrapResponse = studylogScrapService.registerScrap(1L, 1L);

            assertAll(() -> verify(studylogScrapRepository).save(any(StudylogScrap.class)),
                () -> assertThat(memberScrapResponse.getMemberResponse().getId()).isEqualTo(TEST_MEMBER_CREW1.getId()),
                () -> assertThat(memberScrapResponse.getStudylogResponse().getContent()).isEqualTo(
                    TEST_STUDYLOG1.getContent()));
        }
    }

    @Test
    void 스크랩을_제거하는_기능_테스트() {
        StudylogScrap studylogScrap = new StudylogScrap(1L, TEST_MEMBER_CREW1, TEST_STUDYLOG1);

        doReturn(Optional.of(studylogScrap)).when(studylogScrapRepository)
            .findByMemberIdAndStudylogId(anyLong(), anyLong());

        studylogScrapService.unregisterScrap(1L, 1L);

        verify(studylogScrapRepository).delete(studylogScrap);
    }

    @Test
    void 스크랩을_보여주는기능_테스트() {
        List<StudylogScrap> studylogScraps = Arrays.asList(STUDYLOG_SCRAP1, STUDYLOG_SCRAP2);
        Page<StudylogScrap> studylogScrapPages = new PageImpl<>(studylogScraps);

        doReturn(studylogScrapPages).when(studylogScrapRepository).findByMemberId(anyLong(), any(Pageable.class));

        Pageable pageable = mock(Pageable.class);
        StudylogsResponse studylogsResponse = studylogScrapService.showScrap(1L, pageable);

        assertThat(studylogsResponse.getData()).extracting(StudylogResponse::getAuthor)
            .extracting(MemberResponse::getId)
            .contains(STUDYLOG_SCRAP1.getMember().getId(), STUDYLOG_SCRAP2.getMember().getId());
    }
}
