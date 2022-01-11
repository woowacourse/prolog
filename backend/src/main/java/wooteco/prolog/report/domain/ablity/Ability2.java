package wooteco.prolog.report.domain.ablity;

import wooteco.prolog.report.domain.ablity.vo.Children;
import wooteco.prolog.report.domain.ablity.vo.Color;
import wooteco.prolog.report.domain.ablity.vo.Description;
import wooteco.prolog.report.domain.ablity.vo.Name;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.*;

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

    public Ability2(String name, String description, String color, List<Ability2> children) {
        this(new Name(name), new Description(description), new Color(color), new Children(new ArrayList<>(children)));
    }

    public Ability2(Name name, Description description, Color color, Children children) {
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
        this.children.add(ability);
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
