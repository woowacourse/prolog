package wooteco.prolog.report.domain.ablity;

import wooteco.prolog.report.domain.ablity.vo.Children;
import wooteco.prolog.report.domain.ablity.vo.Color;
import wooteco.prolog.report.domain.ablity.vo.Description;
import wooteco.prolog.report.domain.ablity.vo.Name;

public class Ability2 {

    private final Name name;
    private final Description description;
    private final Color color;
    private final Children children;

    public Ability2(Name name, Description description, Color color, Children children) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.children = children;
    }

    public void addChild(Ability2 ability) {
        this.children.add(ability);
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Color getColor() {
        return color;
    }

    public Children getChildren() {
        return children;
    }
}
