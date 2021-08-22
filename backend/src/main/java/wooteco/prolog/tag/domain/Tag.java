package wooteco.prolog.tag.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag extends BaseEntity {

    @Embedded
    private TagName name;

    public Tag(String name) {
        this(null, new TagName(name));
    }

    public Tag(Long id, String name) {
        this(id, new TagName(name));
    }

    public Tag(Long id, TagName name) {
        super(id);
        this.name = name;
    }

    public boolean isSameName(Tag tag) {
        return this.name.equals(tag.name);
    }

    public String getName() {
        return this.name.getValue();
    }
}
