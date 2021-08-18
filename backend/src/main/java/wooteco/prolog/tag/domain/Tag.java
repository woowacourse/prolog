package wooteco.prolog.tag.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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

    public boolean isSameName(Tag tag) {
        return this.name.equals(tag.name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name.getValue();
    }
}
