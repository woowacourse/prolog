package wooteco.prolog.studylog.application;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;


@Component
public class ViewedStudyLogCookieGenerator {

    private static final String VIEWED_STUDY_LOG_COOKIE_NAME = "viewed";

    public ResponseCookie generateCookie(String name, String value) {
        Duration duration = Duration.between(LocalTime.now(), LocalTime.MAX);
        return ResponseCookie.from(name, value)
            .httpOnly(true)
            .sameSite("None")
            .secure(true)
            .maxAge(duration.getSeconds())
            .build();
    }

    public boolean isViewed(String studyLogIds, String studyLogId) {
        return studyLogIds.contains("/" + studyLogId + "/");
    }

    public void setViewedStudyLogCookie(String studyLogIds, String studyLogId,
                                        HttpServletResponse response) {
        if (isViewed(studyLogIds, studyLogId)) {
            return;
        }
        String cookieValue = studyLogIds.concat(studyLogId + "/");
        response.setHeader(HttpHeaders.SET_COOKIE,
            generateCookie(VIEWED_STUDY_LOG_COOKIE_NAME, cookieValue).toString());
    }
}
