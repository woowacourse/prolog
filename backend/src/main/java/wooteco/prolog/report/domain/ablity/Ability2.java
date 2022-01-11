package wooteco.prolog.report.domain.ablity;

import wooteco.prolog.report.domain.ablity.vo.Children;
import wooteco.prolog.report.domain.ablity.vo.Color;
import wooteco.prolog.report.domain.ablity.vo.Description;
import wooteco.prolog.report.domain.ablity.vo.Name;

import java.util.ArrayList;
import java.util.List;

import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.*;

public class Ability2 {

    private final Name name;
    private final Description description;
    private final Color color;
    private final Children children;

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
}
