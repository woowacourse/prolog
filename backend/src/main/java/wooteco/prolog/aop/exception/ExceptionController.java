package wooteco.prolog.aop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wooteco.prolog.post.exception.PostArgumentException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(PostArgumentException.class)
    public ResponseEntity<Void> post4XXExceptionHandling() {
        return ResponseEntity.badRequest().build();
    }
}
