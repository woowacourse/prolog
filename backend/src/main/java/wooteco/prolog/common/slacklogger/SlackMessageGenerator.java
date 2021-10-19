package wooteco.prolog.common.slacklogger;

import static java.util.stream.Collectors.joining;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;

@Component
public class SlackMessageGenerator {

    private static final String EXTRACTION_ERROR_MESSAGE = "메세지를 추출하는데 오류가 생겼습니다.\nmessagee : %s";
    private static final String EXCEPTION_MESSAGE_FORMAT = "_%s_ %s.%s:%d - %s";
    private static final String SLACK_MESSAGE_FORMAT = "*[%s]* %s\n*[요청한 멤버 id]* %s\n\n*[ERROR LOG]*\n%s\n\n*[REQUEST_INFORMATION]*\n%s %s\n%s\n\n%s";
    private static final String EMPTY_BODY_MESSAGE = "{BODY IS EMPTY}";

    private final Environment environment;
    private final JwtTokenProvider jwtTokenProvider;

    public SlackMessageGenerator(Environment environment,
                                 JwtTokenProvider jwtTokenProvider) {
        this.environment = environment;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public SlackMessage generate(ContentCachingRequestWrapper request,
                                 Exception exception,
                                 SlackAlarmErrorLevel level) {
        try {
            String token = AuthorizationExtractor.extract(request);
            String profile = getProfile();
            String currentTime = getCurrentTime();
            String method = request.getMethod();
            String userId = getUserId(token);
            String requestURI = request.getRequestURI();
            String headers = extractHeaders(request);
            String body = getBody(request);
            String exceptionMessage = extractExceptionMessage(exception, level);

            return new SlackMessage(
                toMessage(profile, currentTime, userId,
                    exceptionMessage, method, requestURI, headers, body)
            );
        } catch (Exception e) {
            return new SlackMessage(String.format(EXTRACTION_ERROR_MESSAGE, e.getMessage()));
        }
    }

    private String getProfile() {
        return String.join(",", environment.getActiveProfiles()).toUpperCase();
    }

    private String getCurrentTime() {
        return LocalDateTime.now().toString();
    }

    private String getUserId(String token) {
        return token == null ? "Guest" : jwtTokenProvider.extractSubject(token);
    }

    private String extractHeaders(ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, String> values = new HashMap<>();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            values.put(headerName, request.getHeader(headerName));
        }

        return values.entrySet().stream()
            .map(e -> e.getKey() + ":" + e.getValue())
            .collect(joining("\n"));
    }

    private String getBody(ContentCachingRequestWrapper request) {
        String body = new String(request.getContentAsByteArray());
        if (body.isEmpty()) {
            body = EMPTY_BODY_MESSAGE;
        }
        return body;
    }

    private String extractExceptionMessage(Exception e, SlackAlarmErrorLevel level) {
        StackTraceElement stackTrace = e.getStackTrace()[0];
        String className = stackTrace.getClassName();
        int lineNumber = stackTrace.getLineNumber();
        String methodName = stackTrace.getMethodName();

        String message = e.getMessage();

        if (Objects.isNull(message)) {
            return Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(joining("\n"));
        }

        return String
            .format(EXCEPTION_MESSAGE_FORMAT, level.name(), className, methodName, lineNumber,
                message);
    }


    private String toMessage(String profile, String currentTime, String userId, String errorMessage,
                             String method, String requestURI, String headers, String body) {
        return String.format(
            SLACK_MESSAGE_FORMAT, profile, currentTime, userId,
            errorMessage, method, requestURI, headers, body
        );
    }
}
