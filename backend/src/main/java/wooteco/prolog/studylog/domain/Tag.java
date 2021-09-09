package wooteco.prolog.studylog.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@BatchSize(size = 1000)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TagName name;

    public Tag(String name) {
        this(null, new TagName(name));
    }

    public Tag(Long id, String name) {
        this(id, new TagName(name));
    }

    public Tag(Long id, TagName name) {
        this.id = id;
        this.name = name;
    }

    public boolean isSameName(Tag tag) {
        return this.name.equals(tag.name);
    }

    public String getName() {
        return this.name.getValue();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
