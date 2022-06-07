package wooteco.prolog.ability.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DefaultAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String color;

    private String template;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private DefaultAbility parent;

    public DefaultAbility(String name, String description, String color, String template) {
        this(null, name, description, color, template, null);
    }

    public DefaultAbility(String name, String description, String color,
        String template, DefaultAbility parent) {
        this(null, name, description, color, template, parent);
    }

    public DefaultAbility(Long id, String name, String description, String color,
        String template, DefaultAbility parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.template = template;
        this.parent = parent;
    }

    public boolean isParent() {
        return Objects.isNull(parent);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public DefaultAbility getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultAbility that = (DefaultAbility) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
