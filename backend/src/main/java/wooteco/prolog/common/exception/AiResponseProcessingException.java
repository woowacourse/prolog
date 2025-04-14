package wooteco.prolog.common.exception;

public final class AiResponseProcessingException extends RuntimeException {
    public AiResponseProcessingException(final Throwable cause) {
        super("Invalid response format from AI model", cause);
    }
}
