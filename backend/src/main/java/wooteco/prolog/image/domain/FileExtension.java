package wooteco.prolog.image.domain;

import java.util.stream.Stream;

public enum FileExtension {

    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    BMP("bmp");

    private final String value;

    FileExtension(final String value) {
        this.value = value;
    }

    public static boolean isSupport(final String extension) {
        return Stream.of(values())
                .anyMatch(it -> it.value.equalsIgnoreCase(extension));
    }
}
