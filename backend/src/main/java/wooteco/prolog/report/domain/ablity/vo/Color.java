package wooteco.prolog.report.domain.ablity.vo;

import java.util.Objects;

public class Color {

    private final String value;

    public Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Objects.equals(getValue(), color.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
