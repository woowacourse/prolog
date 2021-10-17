package wooteco.prolog.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;

@Component
public class SlackMessageGenerator {

    private static final String EXTRACTION_ERROR_MESSAGE = "메세지를 추출하는데 오류가 생겼습니다.\nmessagee : %s";
    private static final String format = "[[%s - %s ERROR] %s]\n"
        + "UserId : %s\n"
        + "\n"
        + "%s\n"
        + "\n"
        + "<strong>HTTP Information<strong>\n"
        + "%s %s\n"
        + "body: %s";

    private final Environment environment;
    private final JwtTokenProvider jwtTokenProvider;

    public SlackMessageGenerator(Environment environment,
                                 JwtTokenProvider jwtTokenProvider) {
        this.environment = environment;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Optional<String> generate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String status = String.valueOf(response.getStatus());
            if (!(status.startsWith("4") || status.startsWith("5"))) {
                return Optional.empty();
            }

            String token = AuthorizationExtractor.extract(request);

            String currentTime = LocalDateTime.now().toString();
            String method = request.getMethod();
            String userId = token == null ? "Guest" : jwtTokenProvider.extractSubject(token);
            String profile = String.join(",", environment.getActiveProfiles());
            String requestURI = request.getRequestURI();
            String body = request.getReader().lines().collect(Collectors.joining());

            return Optional
                .of(toMessage(profile, status, currentTime, userId, "테스트 에러 메세지", method, requestURI, body));
        } catch (Exception e) {
            return Optional.of(String.format(EXTRACTION_ERROR_MESSAGE, e.getMessage()));
        }
    }

    private String toMessage(String profile,
                             String status,
                             String currentTime,
                             String userId,
                             String errorMessage,
                             String method,
                             String requestURI,
                             String body
    ) {
        return String.format(format,
            profile,
            status,
            currentTime,
            userId,
            errorMessage,
            method,
            requestURI,
            body
        );
    }
}
