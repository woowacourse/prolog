package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletResponse;

class ViewedStudyLogCookieGeneratorTest {

    private ViewedStudyLogCookieGenerator viewedStudyLogCookieGenerator;

    @BeforeEach
    void init() {
        viewedStudyLogCookieGenerator = new ViewedStudyLogCookieGenerator();
    }

    @DisplayName("generateCookie()를 호출하면 쿠키를 생성하여 반환한다.")
    @Test
    void generateCookie_sucess() {
        //given
        String name = "viewed";
        String value = "3460/";
        ResponseCookie responseCookie = viewedStudyLogCookieGenerator.generateCookie(name, value);

        //when, then
        Assertions.assertAll(
            () -> assertThat(responseCookie).isNotNull(),
            () -> assertThat(responseCookie.getName()).isEqualTo(name),
            () -> assertThat(responseCookie.getValue()).isEqualTo(value)
        );
    }

    @DisplayName("isViewed()를 호출할 때 올바른 형식의 studyLogId를 입력하면 true를 반환한다")
    @Test
    void isViewed_true() {
        //given, when
        boolean actual = viewedStudyLogCookieGenerator.isViewed("/3080/", "3080");

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("isViewed()를 호출할 때 잘못된 형식의 studyLogId를 입력하면 false를 반환한다")
    @Test
    void isViewed_false() {
        //given, when
        boolean actual = viewedStudyLogCookieGenerator.isViewed("/2080/", "3080");

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("setViewedStudyLogCookie()를 호출할 때 올바른 형식의 studyLogId를 입력하면 response 객체에 쿠키 정보를 추가한다")
    @Test
    void setViewedStudyLogCookie_apply() {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String studyLogIds = "/208/";
        String studyLogId = "2080";

        //when
        viewedStudyLogCookieGenerator.setViewedStudyLogCookie(studyLogIds, studyLogId,
            response);
        String cookieValue = response.getHeader(HttpHeaders.SET_COOKIE);

        //then
        assertThat(cookieValue).contains("viewed=" + studyLogIds + studyLogId);
    }

    @DisplayName("setViewedStudyLogCookie()를 호출할 때 올바르지 않는 형식의 studyLogId를 입력하면 아무 동작도 수행하지 않는다")
    @Test
    void setViewedStudyLogCookie_ignore() {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String studyLogIds = "/2080/";
        String studyLogId = "2080";
        String originCookieValue = response.getHeader(HttpHeaders.SET_COOKIE);

        //when
        viewedStudyLogCookieGenerator.setViewedStudyLogCookie(studyLogIds, studyLogId,
            response);
        String cookieValue = response.getHeader(HttpHeaders.SET_COOKIE);

        //then
        assertThat(cookieValue).isEqualTo(originCookieValue);
    }
}
