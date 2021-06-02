package wooteco.prolog.tag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "중복된 태그를 입력할 수 없습니다.")
public class DuplicateTagException extends RuntimeException {

    public DuplicateTagException() {
    }
}
