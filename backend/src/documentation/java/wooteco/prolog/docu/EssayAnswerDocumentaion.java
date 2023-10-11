package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.application.EssayAnswerService;
import wooteco.prolog.roadmap.application.QuizService;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerResponse;
import wooteco.prolog.roadmap.application.dto.EssayAnswerSearchRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerUpdateRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.ui.EssayAnswerController;
import wooteco.prolog.studylog.application.dto.EssayAnswersResponse;

@WebMvcTest(EssayAnswerController.class)
public class EssayAnswerDocumentaion extends NewDocumentation {

    private static final String UTF8_JSON_TYPE = "application/json;charset=UTF-8";

    @MockBean
    EssayAnswerService essayAnswerService;

    @MockBean
    QuizService quizService;


    private static final EssayAnswer ESSAY_ANSWER = new EssayAnswer(
        new Quiz(1L,
            new Keyword(1L, "keyword1", "첫 번째 키워드", 1, 5, 1L, null, Collections.emptySet()),
            "question"),
        "answer",
        new Member(1L, "member1", "nickname1", Role.CREW, 1L, "image.jpg")
    );

    @Test
    void EssayAnswer_생성() {
        given(essayAnswerService.createEssayAnswer(any(), any())).willReturn(1L);
        EssayAnswerRequest request = new EssayAnswerRequest(1L, "answer");

        given
            .contentType(UTF8_JSON_TYPE)
            .body(request)
            .when().post("essay-answers")
            .then().log().all().apply(document("essay-answer/create"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void EssayAnswer_목록_조회() {
        given(essayAnswerService.searchEssayAnswers(any(), any())).willReturn(
            new EssayAnswersResponse(
                Arrays.asList(
                    EssayAnswerResponse.of(ESSAY_ANSWER),
                    EssayAnswerResponse.of(ESSAY_ANSWER)),
                1L, 1, 1));
        EssayAnswerSearchRequest request = new EssayAnswerSearchRequest(1L, 2L, Collections.emptyList(),
            Collections.emptyList());

        given
            .contentType(UTF8_JSON_TYPE)
            .body(request)
            .when().get("essay-answers")
            .then().log().all().apply(document("essay-answer/search/list"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void EssayAnswer_업데이트() {
        EssayAnswerUpdateRequest request = new EssayAnswerUpdateRequest("new Answer");

        given
            .contentType(UTF8_JSON_TYPE)
            .body(request)
            .when().patch("essay-answers/{id}", 1)
            .then().log().all().apply(document("essay-answer/update"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void EssayAnswer_삭제() {
        given
            .when().delete("essay-answers/{id}", 1)
            .then().log().all().apply(document("essay-answer/delete"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Quiz_에_대한_EssayAnswerResponse_조회() {
        given(essayAnswerService.findByQuizId(anyLong()))
            .willReturn(Arrays.asList(ESSAY_ANSWER, ESSAY_ANSWER));

        given
            .when().get("quizzes/{id}/essay-answers", 1)
            .then().log().all().apply(document("essay-answer/quiz/essay-answer"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void EssayAnswer_조회() {
        given(essayAnswerService.getById(anyLong()))
            .willReturn(ESSAY_ANSWER);

        given
            .when().get("essay-answers/{id}", 1)
            .then().log().all().apply(document("essay-answer/search"))
            .statusCode(HttpStatus.OK.value());
    }
}
