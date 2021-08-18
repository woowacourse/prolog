package wooteco.prolog.tag.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.tag.exception.TagNameNullOrEmptyException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
public class TagName {

    @Column(name = "name")
    private String value;

    public TagName(String name) {
        validateNull(name);
        validateEmpty(name);
        validateOnlyBlank(name);
        this.value = trim(name);
    }

    private String trim(String name) {
        return name.trim();
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new TagNameNullOrEmptyException();
        }
    }

    private void validateEmpty(String name) {
        if (name.isEmpty()) {
            throw new TagNameNullOrEmptyException();
        }
    }

    private void validateOnlyBlank(String name) {
        if (name.trim().isEmpty()) {
            throw new TagNameNullOrEmptyException();
        }
    }

    public String getValue() {
        return value;
    }
}
