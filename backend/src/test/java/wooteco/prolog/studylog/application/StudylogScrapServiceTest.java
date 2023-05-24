package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.prolog.studylog.exception.StudylogScrapAlreadyRegisteredException;
import wooteco.prolog.studylog.exception.StudylogScrapNotExistException;

@ExtendWith(MockitoExtension.class)
class StudylogScrapServiceTest {

    @Mock
    private StudylogScrapRepository studylogScrapRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private StudylogRepository studylogRepository;
    @InjectMocks
    private StudylogScrapService studylogScrapService;

    @DisplayName("학습로그 스크랩을 등록 후, 멤버의 학습로그 스크랩 정보를 반환한다.")
    @Test
    void registerScrap_success() {
        //given
        final Member 멤버_스플릿 = new Member(1L, "박상현", "스플릿",
            Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 스플릿_학습로그 = new Studylog(멤버_스플릿, "title", "content",
            자동차_미션, 자바_스프링_태그_목록);
        final StudylogScrap 스플릿_학습로그_스크랩 = new StudylogScrap(멤버_스플릿, 스플릿_학습로그);

        when(studylogScrapRepository.countByMemberIdAndScrapStudylogId(anyLong(), anyLong()))
            .thenReturn(0);
        when(studylogRepository.findById(anyLong()))
            .thenReturn(Optional.of(스플릿_학습로그));
        when(memberRepository.findById(anyLong()))
            .thenReturn(Optional.of(멤버_스플릿));
        when(studylogScrapRepository.save(any()))
            .thenReturn(스플릿_학습로그_스크랩);

        //when
        MemberScrapResponse memberScrapResponse = studylogScrapService.registerScrap(1L, 1L);

        //then
        assertAll(
            () -> assertThat(memberScrapResponse.getMemberResponse().getId()).isEqualTo(멤버_스플릿.getId()),
            () -> assertThat(memberScrapResponse.getStudylogResponse().getContent())
                .isEqualTo(스플릿_학습로그.getContent()));
    }

    @DisplayName("등록하려는 학습로그 스크랩이 이미 등록된 경우 예외가 발생한다.")
    @Test
    void registerScrap_fail_scrapExist() {
        //given
        when(studylogScrapRepository.countByMemberIdAndScrapStudylogId(anyLong(), anyLong()))
            .thenReturn(1);

        //when
        //then
        assertThatThrownBy(() -> studylogScrapService.registerScrap(1L, 2L))
            .isInstanceOf(StudylogScrapAlreadyRegisteredException.class);
    }

    @DisplayName("학습로그 스크랩 등록 시 등록하려는 학습로그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void registerScrap_fail_studylogNotExist() {
        //given
        when(studylogScrapRepository.countByMemberIdAndScrapStudylogId(anyLong(), anyLong()))
            .thenReturn(0);
        when(studylogRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> studylogScrapService.registerScrap(1L, 2L))
            .isInstanceOf(StudylogNotFoundException.class);
    }

    @DisplayName("학습로그 스크랩 등록 시 멤버가 존재하지 않으면 예외가 발생한다.")
    @Test
    void registerScrap_fail_memberNotExist() {
        //given
        final Member 멤버_스플릿 = new Member(1L, "박상현", "스플릿",
            Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 스플릿_학습로그 = new Studylog(멤버_스플릿, "title", "content",
            자동차_미션, 자바_스프링_태그_목록);

        when(studylogScrapRepository.countByMemberIdAndScrapStudylogId(anyLong(), anyLong()))
            .thenReturn(0);
        when(studylogRepository.findById(anyLong()))
            .thenReturn(Optional.of(스플릿_학습로그));
        when(memberRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> studylogScrapService.registerScrap(1L, 2L))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("학습로그 스크랩을 등록 해제한다.")
    @Test
    void unregisterScrap_success() {
        //given
        final Member 멤버_스플릿 = new Member(1L, "박상현", "스플릿",
            Role.CREW, 1L, "imageUrl");
        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);
        final Studylog 스플릿_학습로그 = new Studylog(멤버_스플릿, "title", "content",
            자동차_미션, 자바_스프링_태그_목록);
        final StudylogScrap 스플릿_학습로그_스크랩 = new StudylogScrap(멤버_스플릿, 스플릿_학습로그);

        when(studylogScrapRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
            .thenReturn(Optional.of(스플릿_학습로그_스크랩));

        //when
        studylogScrapService.unregisterScrap(1L, 1L);

        //then
        verify(studylogScrapRepository).delete(스플릿_학습로그_스크랩);
    }

    @DisplayName("학습로그 스크랩 등록 해제 시 스크랩이 존재하지 않으면 예외가 발생한다.")
    @Test
    void unregisterScrap_fail_scrapNotExist() {
        //given
        when(studylogScrapRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
            .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() ->  studylogScrapService.unregisterScrap(1L, 1L))
            .isInstanceOf(StudylogScrapNotExistException.class);
    }


    @DisplayName("스크랩을 보여주는 기능 테스트")
    @Test
    void showScrap_success() {
        //given
        final Member 멤버_스플릿 = new Member(1L, "박상현", "스플릿",
            Role.CREW, 1L, "imageUrl");
        final Member 멤버_져니 = new Member(2L, "이지원", "져니",
            Role.CREW, 2L, "imageUrl");

        final Session 세션 = new Session("세션 2");
        final Mission 자동차_미션 = new Mission("자동차 미션", 세션);
        final Tag 자바_태그 = new Tag("Java");
        final Tag 스프링_태그 = new Tag("Spring");
        final List<Tag> 자바_스프링_태그_목록 = Arrays.asList(자바_태그, 스프링_태그);


        final Studylog 스플릿_학습로그 = new Studylog(멤버_스플릿, "title", "content",
            자동차_미션, 자바_스프링_태그_목록);
        final Studylog 져니_학습로그 = new Studylog(멤버_져니, "title", "content",
            자동차_미션, 자바_스프링_태그_목록);

        final StudylogScrap 스플릿_학습로그_스크랩 = new StudylogScrap(멤버_스플릿, 스플릿_학습로그);
        final StudylogScrap 져니_학습로그_스크랩 = new StudylogScrap(멤버_져니, 져니_학습로그);

        final List<StudylogScrap> studylogScraps = Arrays.asList(스플릿_학습로그_스크랩, 져니_학습로그_스크랩);
        final Page<StudylogScrap> studylogScrapPages = new PageImpl<>(studylogScraps);
        final PageRequest pageRequest = PageRequest.of(1, 10);

        when(studylogScrapRepository.findByMemberId(anyLong(), any()))
            .thenReturn(studylogScrapPages);

        //when
        final StudylogsResponse studylogsResponse = studylogScrapService.showScrap(1L, pageRequest);

        //then
        assertThat(studylogsResponse.getData())
            .extracting(StudylogResponse::getAuthor)
            .extracting(MemberResponse::getId)
            .contains(스플릿_학습로그_스크랩.getMember().getId(), 져니_학습로그_스크랩.getMember().getId());
    }
}
