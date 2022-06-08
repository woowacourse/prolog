package wooteco.prolog.ability.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String color;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Ability parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Ability> children;

    @OneToMany(mappedBy = "ability", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StudylogAbility> studylogAbilities;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Ability(Long id, String name, String description, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    private Ability(Long id, String name, String description, String color, Ability parent,
                    Member member) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.member = member;
    }

    public static Ability parent(String name, String description, String color, Member member) {
        return parent(null, name, description, color, member);
    }

    public static Ability parent(Long id, String name, String description, String color,
                                 Member member) {
        return new Ability(id, name, description, color, null, member);
    }

    public static Ability child(String name, String description, String color, Ability parent,
                                Member member) {
        return child(null, name, description, color, parent, member);
    }

    public static Ability child(Long id, String name, String description, String color,
                                Ability parent, Member member) {
        Ability child = new Ability(id, name, description, color, parent, member);
        parent.addChildAbility(child);

        return child;
    }

    public void addChildAbility(Ability childAbility) {
        children.add(childAbility);
    }

    public void update(Ability updateAbility) {
        this.name = updateAbility.name;
        this.description = updateAbility.description;
        updateColor(updateAbility);
    }

    private void updateColor(Ability updateAbility) {
        if (!this.isParent()) {
            return;
        }

        this.color = updateAbility.color;

        for (Ability childAbility : getChildren()) {
            childAbility.color = updateAbility.getColor();
        }
    }

    public boolean isParent() {
        return Objects.isNull(parent);
    }

    public void validateDuplicateName(List<Ability> abilities) {
        if (abilities.stream().anyMatch(this::isSameName)) {
            throw new AbilityNameDuplicateException();
        }
    }

    private boolean isSameName(Ability ability) {
        return name.equals(ability.name);
    }

    public void validateColorWithParent(List<Ability> abilities, Ability parentAbility) {
        abilities.remove(parentAbility);
        abilities.removeAll(parentAbility.getChildren());

        validateSameColor(parentAbility);
        validateDuplicateColor(abilities);
    }

    public void validateDuplicateColor(List<Ability> abilities, Ability ability) {
        abilities.remove(ability);
        abilities.removeAll(ability.getChildren());

        validateDuplicateColor(abilities);
    }

    private void validateSameColor(Ability parentAbility) {
        if (isDifferentColor(parentAbility)) {
            throw new AbilityParentChildColorDifferentException();
        }
    }

    public void validateDuplicateColor(List<Ability> abilities) {
        if (abilities.stream().anyMatch(this::isSameColor)) {
            throw new AbilityParentColorDuplicateException();
        }
    }

    private boolean isDifferentColor(Ability ability) {
        return !isSameColor(ability);
    }

    private boolean isSameColor(Ability ability) {
        return color.equals(ability.color);
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
        return children;
    }

    public List<StudylogAbility> getStudylogAbilities() {
        return studylogAbilities;
    }

    public boolean isBelongsTo(Long memberId) {
        return this.member.getId() == memberId;
    }
}
