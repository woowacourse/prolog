package wooteco.prolog.report.domain.ablity;

import wooteco.prolog.report.domain.ablity.vo.Children;
import wooteco.prolog.report.domain.ablity.vo.Color;
import wooteco.prolog.report.domain.ablity.vo.Description;
import wooteco.prolog.report.domain.ablity.vo.Name;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateSameColor;
import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateSameName;

@Entity
public class Ability2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Description description;

    @Embedded
    private Color color;

    @Embedded
    private Children children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Ability2 parent;

    protected Ability2() {
    }

    public Ability2(String name, String description, String color, List<AbilityChild> children) {
        this(null, new Name(name), new Description(description), new Color(color), new Children(new ArrayList<>(children)));
    }

    public Ability2(Name name, Description description, Color color, Children children) {
        this(null, name, description, color, children);
    }

    public Ability2(Long id, String name, String description, String color, List<AbilityChild> children) {
        this(id, new Name(name), new Description(description), new Color(color), new Children(new ArrayList<>(children)));
    }

    public Ability2(Long id, Name name, Description description, Color color, Children children) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.children = children;

        children.getValues().forEach(childAbility -> validateSameColor(this.color, childAbility.color));
        children.getValues().forEach(childAbility -> validateSameName(this.name, childAbility.name));
    }

    public void addChild(Ability2 ability) {
        validateSameColor(this.color, ability.color);
        validateSameName(this.name, ability.name);
        this.children.add(this, ability);
    }

    public boolean isSameName(Ability2 ability) {
        return isSameName(ability.name);
    }

    public boolean isSameName(Name name) {
        return this.name.equals(name);
    }

    public boolean isSameColor(Ability2 ability2) {
        return isSameColor(ability2.color);
    }

    public boolean isSameColor(Color color) {
        return this.color.equals(color);
    }

    public boolean isParent() {
        return Objects.isNull(parent);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public String getColor() {
        return color.getValue();
    }

    public List<Ability2> getChildren() {
        return children.getValues();
    }

    public boolean isExactlyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability2 ability2 = (Ability2) o;
        return Objects.equals(getId(), ability2.getId()) &&
                Objects.equals(getName(), ability2.getName()) &&
                Objects.equals(getDescription(), ability2.getDescription()) &&
                Objects.equals(getColor(), ability2.getColor()) &&
                Objects.equals(getChildren(), ability2.getChildren()) &&
                Objects.equals(isParent(), ability2.isParent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability2 ability2 = (Ability2) o;
        return Objects.equals(id, ability2.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
