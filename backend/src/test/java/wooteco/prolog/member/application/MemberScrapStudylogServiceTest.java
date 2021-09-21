package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
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
import wooteco.prolog.member.exception.MemberScrapNotExistException;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.member.util.MemberUtilCRUD;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.studylog.util.StudylogFixture;
import wooteco.prolog.studylog.studylog.util.StudylogUtilCRUD;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
@Transactional
public class MemberScrapStudylogServiceTest {

    private static final int FIRST_DATA = 0;

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

    @DisplayName("스크랩 조회기능 및 페이지네이션 확인")
    @ParameterizedTest
    @CsvSource({"0,10,30,title30", "0,50,50,title50", "2,15,40,title10", "1,12,36,title24"})
    void showScrapTest(int page, int size, int totalSize, String expectedTitle) {
        //given
        for (int id = 1; id <= totalSize; id++) {
            studylogUtilCRUD.등록(MemberFixture.웨지, "title" + (id + 1), "content", 1L);
            memberScrapService.registerScrap(member, (long) id);
        }
        //when
        StudylogsResponse studylogsResponse = memberScrapService
            .showScrap(member, PageRequest.of(page, size, Direction.DESC, "id"));
        //then
        StudylogResponse firstData = studylogsResponse.getData().get(FIRST_DATA);
        assertThat(firstData.getTitle()).isEqualTo(expectedTitle);
        assertThat(studylogsResponse.getTotalSize()).isEqualTo(totalSize);
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
        StudylogsResponse studylogsResponse = memberScrapService
            .showScrap(member, Pageable.unpaged());

        assertThat(studylogsResponse.getTotalSize()).isEqualTo(0);
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
