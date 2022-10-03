package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.MEMBER;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
//        given(studylogService.readRssFeeds(any()))
//                .willReturn(List.of(
//                        StudylogRssFeedResponse.of(
//                                new Studylog(MEMBER, "Prolog | 우아한테크코스 학습로그 저장소","dd",new Session(1L, "세션1"),
//                                        new Mission("자동차미션", new Session(1L, "레벨1")), List.of(new Tag(1L, "자바")))
//                                ,"http://localhost:8080"))
//                );

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
//
//    private ExtractableResponse<Response> 스터디로그_등록함(List<StudylogRequest> request) {
//        return RestAssured.given().log().all()
//            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
//            .body(request)
//            .contentType(APPLICATION_JSON_VALUE)
//            .when().log().all()
//            .post("/studylogs")
//            .then().log().all().extract();
//    }
//
//    private StudylogRequest studylogRequest1() {
//        Long sessionId = 세션_등록함(new SessionRequest("세션 1"));
//        Long missionId = 미션_등록함(new MissionRequest("세션 1 - 어떤 미션", sessionId));
//
//        return new StudylogRequest(
//            "어떤 타이틀",
//            "어떤 내용",
//            missionId,
//            Arrays.asList(new TagRequest("어떤 태그 1"), new TagRequest("어떤 태그 2"))
//        );
//    }
//
//    private StudylogRequest studylogRequest2() {
//        Long sessionId = 세션_등록함(new SessionRequest("세션 2"));
//        Long missionId = 미션_등록함(new MissionRequest("세션 2 - 어쩌구 미션", sessionId));
//
//        return new StudylogRequest(
//            "어쩌구 타이틀",
//            "어쩌구 내용",
//            missionId,
//            Arrays.asList(new TagRequest("어쩌구 태그 1"), new TagRequest("어쩌구 태그 2"))
//        );
//    }
//
//    private Long 세션_등록함(SessionRequest request) {
//        return RestAssured
//            .given().log().all()
//            .body(request)
//            .contentType(APPLICATION_JSON_VALUE)
//            .when()
//            .post("/sessions")
//            .then()
//            .log().all()
//            .extract()
//            .as(SessionResponse.class)
//            .getId();
//    }
//
//    private Long 미션_등록함(MissionRequest request) {
//        return RestAssured.given()
//            .body(request)
//            .contentType(APPLICATION_JSON_VALUE)
//            .when()
//            .post("/missions")
//            .then()
//            .log().all()
//            .extract()
//            .as(MissionResponse.class)
//            .getId();
//    }
}
