package wooteco.prolog.docu;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import wooteco.prolog.roadmap.application.QuizService;
import wooteco.prolog.roadmap.application.dto.QuizRequest;

public class QuizDocumentation {

    @MockBean
    private QuizService quizService;

    @Test
    void 퀴즈를_생성한다() {
        final QuizRequest 수달이_루키_이름으로_만든_퀴즈_요청 = new QuizRequest("수달이 루키 이름으로 만든 퀴즈");
        //given

//        when(quizService.createQuiz(1L, 수달이_루키_이름으로_만든_퀴즈_요청)).thenReturn(1L);

//        //when
//        ValidatableMockMvcResponse response = given
//            .body(수달이_루키_이름으로_만든_퀴즈_요청)
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .when().post("/sessions/{sessionId}/keywords/{keywordId}/quizs", 1L, 1L)
//            .then().log().all();
//
//        //then
//        response.expect(status().isCreated());
//
//        //docs
//        response.apply(document("quiz/quiz"));
    }

}
