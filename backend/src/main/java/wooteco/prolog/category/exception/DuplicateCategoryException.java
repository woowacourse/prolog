package wooteco.prolog.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "카테고리가 중복됩니다.")
public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException() {
    }
}
