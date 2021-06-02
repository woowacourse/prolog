package wooteco.prolog.login.application.dto;

public class ErrorMessage {

    private String message;

    public ErrorMessage() {

    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
