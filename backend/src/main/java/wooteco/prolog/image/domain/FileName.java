package wooteco.prolog.image.domain;

import java.util.Objects;
import lombok.Getter;
import wooteco.prolog.image.exception.FileNameEmptyException;

@Getter
public class FileName {

    private final String value;

    public FileName(final String value) {
        validate(value);
        this.value = value;
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value);
        if (value.trim().isEmpty()) {
            throw new FileNameEmptyException();
        }
    }
}
