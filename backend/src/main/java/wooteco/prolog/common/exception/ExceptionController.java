package wooteco.prolog.common.exception;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.testcontainers.shaded.org.bouncycastle.math.ec.ECAlgorithms;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> badRequestExceptionHandler(BadRequestException e) {
        log.warn(e.getMessage());
        return ResponseEntity.badRequest()
            .body(new ExceptionDto(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ElasticsearchCustomException.class)
    public ResponseEntity<ExceptionDto> elasticsearchCustomExceptionHandler(ElasticsearchCustomException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionDto(500, "알 수 없는 에러"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> runtimeExceptionHandler(Exception e) {
        if (e.getMessage() == null) {
            log.error(e.getClass().toString());
            log.error(Arrays.stream(e.getStackTrace()).map(it -> it.toString())
                .collect(Collectors.joining("\n")));
        } else {
            log.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionDto(500, "알 수 없는 에러"));
    }
}
