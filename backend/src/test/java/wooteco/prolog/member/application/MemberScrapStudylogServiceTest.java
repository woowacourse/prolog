package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.exception.MemberScrapNotExistException;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.member.util.MemberUtilCRUD;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.studylog.util.StudylogFixture;
import wooteco.prolog.studylog.studylog.util.StudylogUtilCRUD;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
@Transactional
public class MemberScrapStudylogServiceTest {

    @Autowired
    private MemberUtilCRUD memberUtilCRUD;
    @Autowired
    private StudylogUtilCRUD studylogUtilCRUD;
    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private MemberScrapService memberScrapService;

    private Member member;
    private Studylog studylog;

    @BeforeEach
    void setUp() {
        member = memberUtilCRUD.등록(MemberFixture.웨지);
        studylogUtilCRUD.등록(StudylogFixture.로또_미션_정리, MemberFixture.웨지);
        studylog = studylogRepository.findAll().get(0);
    }

    @DisplayName("스크랩 등록기능 확인")
    @Test
    void registerScrapTest() {
        //given
        MemberScrapResponse memberScrapResponse = memberScrapService
            .registerScrap(member, studylog.getId());
        //when
        MemberResponse memberResponse = memberScrapResponse.getMemberResponse();
        StudylogResponse studylogResponse = memberScrapResponse.getStudylogResponse();
        //then
        assertThat(member.getId()).isEqualTo(memberResponse.getId());
        assertThat(studylog.getId()).isEqualTo(studylogResponse.getId());
    }

    @DisplayName("스크랩 삭제기능 확인")
    @Test
    void unregisterScrapTest() {
        //given
        MemberScrapResponse memberScrapResponse = memberScrapService
            .registerScrap(member, studylog.getId());
        //when
        Long studylogId = memberScrapResponse.getStudylogResponse().getId();
        memberScrapService.unregisterScrap(member, studylogId);
        //then
        //todo : 조회기능 추가시 작성
    }

    @DisplayName("[예외] 없는 스크랩을 삭제하려고 하면 예외")
    @Test
    void whenNotExistScrapDeleteTest() {
        //given
        //when
        //then
        assertThatThrownBy(() -> memberScrapService.unregisterScrap(member, studylog.getId()))
                .isInstanceOf(MemberScrapNotExistException.class);
    }
}
