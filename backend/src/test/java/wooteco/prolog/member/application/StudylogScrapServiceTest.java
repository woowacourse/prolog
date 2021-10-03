package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.exception.StudylogScrapNotExistException;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.member.util.MemberUtilCRUD;
import wooteco.prolog.studylog.application.StudylogScrapService;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.studylog.util.StudylogFixture;
import wooteco.prolog.studylog.studylog.util.StudylogUtilCRUD;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
@Transactional
public class StudylogScrapServiceTest {

    private static final int FIRST_DATA = 0;

    @Autowired
    private MemberUtilCRUD memberUtilCRUD;
    @Autowired
    private StudylogUtilCRUD studylogUtilCRUD;
    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private StudylogScrapService studylogScrapService;

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
        MemberScrapResponse memberScrapResponse = studylogScrapService
            .registerScrap(member.getId(), studylog.getId());
        //when
        MemberResponse memberResponse = memberScrapResponse.getMemberResponse();
        StudylogResponse studylogResponse = memberScrapResponse.getStudylogResponse();
        //then
        assertThat(member.getId()).isEqualTo(memberResponse.getId());
        assertThat(studylog.getId()).isEqualTo(studylogResponse.getId());
    }

    @DisplayName("스크랩 조회기능 및 페이지네이션 확인")
    @ParameterizedTest
    @CsvSource({"0,10,30,title30", "0,50,50,title50", "2,15,40,title10", "1,12,36,title24"})
    void showScrapTest(int page, int size, int totalSize, String expectedTitle) {
        //given
        for (int id = 1; id <= totalSize; id++) {
            studylogUtilCRUD.등록(MemberFixture.웨지, "title" + (id + 1), "content", 1L);
            studylogScrapService.registerScrap(member.getId(), (long) id);
        }
        //when
        StudylogsResponse studylogsResponse = studylogScrapService
            .showScrap(member.getId(), PageRequest.of(page, size, Direction.DESC, "id"));
        //then
        StudylogResponse firstData = studylogsResponse.getData().get(FIRST_DATA);
        assertThat(firstData.getTitle()).isEqualTo(expectedTitle);
        assertThat(studylogsResponse.getTotalSize()).isEqualTo(totalSize);
    }

    @DisplayName("스크랩 삭제기능 확인")
    @Test
    void unregisterScrapTest() {
        //given
        MemberScrapResponse memberScrapResponse = studylogScrapService
            .registerScrap(member.getId(), studylog.getId());
        //when
        Long studylogId = memberScrapResponse.getStudylogResponse().getId();
        studylogScrapService.unregisterScrap(member.getId(), studylogId);
        //then
        StudylogsResponse studylogsResponse = studylogScrapService
            .showScrap(member.getId(), Pageable.unpaged());

        assertThat(studylogsResponse.getTotalSize()).isEqualTo(0);
    }

    @DisplayName("[예외] 없는 스크랩을 삭제하려고 하면 예외")
    @Test
    void whenNotExistScrapDeleteTest() {
        //given
        //when
        //then
        assertThatThrownBy(() -> studylogScrapService.unregisterScrap(member.getId(), studylog.getId()))
            .isInstanceOf(StudylogScrapNotExistException.class);
    }
}
