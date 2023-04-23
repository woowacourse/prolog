package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static wooteco.prolog.studylog.StudylogFixture.TEST_MEMBER_CREW1;
import static wooteco.prolog.studylog.StudylogFixture.TEST_MEMBER_CREW2;
import static wooteco.prolog.studylog.StudylogFixture.TEST_STUDYLOG;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.exception.StudylogScrapAlreadyRegisteredException;

@ExtendWith(MockitoExtension.class)
class StudylogScrapServiceTest {

    public static final StudylogScrap STUDYLOG_SCRAP1 = new StudylogScrap(1L, TEST_MEMBER_CREW1, null);
    public static final StudylogScrap STUDYLOG_SCRAP2 = new StudylogScrap(2L, TEST_MEMBER_CREW2, null);

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
            doReturn(1).when(studylogScrapRepository)
                .countByMemberIdAndScrapStudylogId(anyLong(), anyLong());

            assertThatThrownBy(() -> studylogScrapService.registerScrap(1L, 2L))
                .isInstanceOf(StudylogScrapAlreadyRegisteredException.class);
        }

        @Test
        void 정상적으로_스크랩을_등록하는_기능_테스트() {
            doReturn(0).when(studylogScrapRepository).countByMemberIdAndScrapStudylogId(anyLong(), anyLong());
            doReturn(Optional.of(TEST_STUDYLOG)).when(studylogRepository).findById(anyLong());
            doReturn(Optional.of(TEST_MEMBER_CREW1)).when(memberRepository).findById(anyLong());

            MemberScrapResponse memberScrapResponse = studylogScrapService.registerScrap(1L, 1L);

            assertAll(() -> verify(studylogScrapRepository).save(any(StudylogScrap.class)),
                () -> assertThat(memberScrapResponse.getMemberResponse().getId())
                    .isEqualTo(TEST_MEMBER_CREW1.getId()),
                () -> assertThat(memberScrapResponse.getStudylogResponse().getContent()).isEqualTo(
                    TEST_STUDYLOG.getContent()));
        }
    }

    @Test
    void 스크랩을_제거하는_기능_테스트() {
        StudylogScrap studylogScrap = new StudylogScrap(1L, TEST_MEMBER_CREW1, TEST_STUDYLOG);

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

        Assertions.assertThat(studylogsResponse.getData()).extracting(StudylogResponse::getId)
            .contains(STUDYLOG_SCRAP1.getId(), STUDYLOG_SCRAP2.getId());
    }
}
