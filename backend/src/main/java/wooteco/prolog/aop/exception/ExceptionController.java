package wooteco.prolog.aop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wooteco.prolog.post.exception.PostArgumentException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(PostArgumentException.class)
    public ResponseEntity<Void> post4XXExceptionHandling() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unexpectedExceptionHandling(Exception e) {
        printErrorLog(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private void printErrorLog(Exception e) {
        log.info(e.toString());
        Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .forEach(log::info);
    }
}
