package wooteco.prolog.mission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "존재하지 않는 미션입니다.")
public class MissionNotFoundException extends RuntimeException {
    public MissionNotFoundException() {

    }
}
