package wooteco.prolog.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final String message;
    private final int code;

    public BadRequestException(BadRequestCode badRequestCode) {
        this.message = badRequestCode.getMessage();
        this.code = badRequestCode.getCode();
    }
}
