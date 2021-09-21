package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
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
public class MemberScrapServiceTest {

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
            .insertScrap(member, studylog.getId());
        //when
        MemberResponse memberResponse = memberScrapResponse.getMemberResponse();
        StudylogResponse studylogResponse = memberScrapResponse.getStudylogResponse();
        //then
        assertThat(member.getId()).isEqualTo(memberResponse.getId());
        assertThat(studylog.getId()).isEqualTo(studylogResponse.getId());
    }
}
