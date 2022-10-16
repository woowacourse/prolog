package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.MEMBER;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.ui.StudylogRssFeedController;
import wooteco.prolog.studylog.ui.StudylogRssFeedView;

@WebMvcTest(controllers = StudylogRssFeedController.class)
class StudylogRssFeedDocumentation extends NewDocumentation {

    @MockBean
    private StudylogService studylogService;

    @MockBean
    private StudylogRssFeedView studylogRssFeedView;

    @Test
    void RSS_피드를_조회한다() {
        //given
        given(studylogService.readRssFeeds(any()))
                .willReturn(List.of(
                        StudylogRssFeedResponse.of(
                                new Studylog(MEMBER, "Prolog | 우아한테크코스 학습로그 저장소","dd",new Session(1L, "세션1"),
                                        new Mission("자동차미션", new Session(1L, "레벨1")), List.of(new Tag(1L, "자바")))
                                ,"http://localhost:8080"))
                );

        //when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_RSS_XML_VALUE)
                .when().get("/rss")
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("rss-feed/read"));
    }
}
