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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.exception.AbilityHasChildrenException;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected Ability() {
    }

    public Ability(Long id, String name, String description, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    private Ability(Long id, String name, String description, String color, Ability parent, Member member) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.member = member;
        this.member.addAbility(this);
    }

    public static Ability parent(String name, String description, String color, Member member) {
        return parent(null, name, description, color, member);
    }

    public static Ability parent(Long id, String name, String description, String color, Member member) {
        return new Ability(id, name, description, color, null, member);
    }

    public static Ability child(String name, String description, String color, Ability parent, Member member) {
        return child(null, name, description, color, parent, member);
    }

    public static Ability child(Long id, String name, String description, String color, Ability parent, Member member) {
        Ability child = new Ability(id, name, description, color, parent, member);
        parent.addChildAbility(child);

        return child;
    }

    public void addChildAbility(Ability ability) {
        AbilityRelationship abilityRelationship = new AbilityRelationship(this, ability);
        this.children.add(abilityRelationship);
    }

    public void update(Ability updateAbility) {
        this.name = updateAbility.name;
        this.description = updateAbility.description;
        this.color = updateAbility.color;
    }

    public boolean isParent() {
        return Objects.isNull(parent);
    }

    public void validateDeletable() {
        if (!children.isEmpty()) {
            throw new AbilityHasChildrenException();
        }
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
        return Objects.equals(id, ability.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
