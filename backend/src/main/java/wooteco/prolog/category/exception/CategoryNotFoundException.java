package wooteco.prolog.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "존재하지 않는 카테고리입니다.")
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {

    }
}
