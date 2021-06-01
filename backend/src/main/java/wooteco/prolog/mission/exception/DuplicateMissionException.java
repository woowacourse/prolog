package wooteco.prolog.mission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "미션이 중복됩니다.")
public class DuplicateMissionException extends RuntimeException {
    public DuplicateMissionException() {
    }
}
