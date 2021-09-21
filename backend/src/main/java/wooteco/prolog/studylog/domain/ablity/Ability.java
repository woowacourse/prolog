package wooteco.prolog.studylog.domain.ablity;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String color;

    @OneToOne
    private Ability parent;

    @OneToMany(mappedBy = "target", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AbilityRelationship> children;

    protected Ability() {
    }

    public static Ability parent(String name, String description, String color) {
        return parent(null, name, description, color);
    }

    public static Ability parent(Long id, String name, String description, String color) {
        return new Ability(id, name, description, color, null);
    }

    public static Ability child(String name, String description, String color, Ability parent) {
        return child(null, name, description, color, parent);
    }

    public static Ability child(Long id, String name, String description, String color, Ability parent) {
        return new Ability(id, name, description, color, parent);
    }

    private Ability(Long id, String name, String description, String color, Ability parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public void addChildAbility(Ability ability) {
        AbilityRelationship abilityRelationship = new AbilityRelationship(this, ability);
        this.children.add(abilityRelationship);
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

    public Ability getParent() {
        return parent;
    }

    public List<Ability> getChildren() {
        return children.stream()
            .map(AbilityRelationship::getTarget)
            .collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ability)) {
            return false;
        }
        Ability ability = (Ability) o;
        return Objects.equals(getId(), ability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
