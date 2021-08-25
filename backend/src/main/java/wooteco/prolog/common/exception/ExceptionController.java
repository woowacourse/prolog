package wooteco.prolog.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDto> loginExceptionHandler(BadRequestException e) {
        return ResponseEntity.badRequest()
                .body(new BadRequestExceptionDto(e.getCode(), e.getMessage()));
    }
}
