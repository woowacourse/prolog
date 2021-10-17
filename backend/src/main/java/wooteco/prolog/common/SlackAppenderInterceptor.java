package wooteco.prolog.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class SlackAppenderInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {

        String status = String.valueOf(response.getStatus());

        if(status.startsWith("5")) {
            log.error("500에러입니다.");
        }

        if(status.startsWith("4")) {
            log.warn("400에러입니다.");
        }
    }
}
