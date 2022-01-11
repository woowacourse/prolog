package wooteco.prolog.report.domain.ablity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.report.domain.ablity.vo.Children;
import wooteco.prolog.report.domain.ablity.vo.Color;
import wooteco.prolog.report.domain.ablity.vo.Name;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class Ability2Test {

    private Ability2 ability;

    @BeforeEach
    void setUp() {
        ability = new Ability2("역량", "역량입니다", "1234", new ArrayList<>());
    }

    @DisplayName("역량을 생성한다 - 성공")
    @Test
    void create() {
        assertThatCode(() -> new Ability2("역량", "역량입니다", "1234", new ArrayList<>()))
                .doesNotThrowAnyException();
    }

    @DisplayName("생성시 부모 역량과 같은 이름을 가진 자식역량을 추가할 수 없다.")
    @Test
    void create_isSameNameWithParent() {
        assertThatThrownBy(() -> new Ability2(
                "역량",
                "역량입니다",
                "1234",
                Collections.singletonList(
                        new Ability2(
                                "역량",
                                "역량입니다",
                                "1234",
                                new ArrayList<>())
                )
        )).isInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("생성시 부모 역량과 다른 색을 가진 자식역량을 추가할 수 없다.")
    @Test
    void create_isSameColorWithParent() {
        assertThatThrownBy(() -> new Ability2(
                        "역량",
                        "역량입니다",
                        "1234",
                        Collections.singletonList(
                                new Ability2(
                                        "자식역량",
                                        "역량입니다",
                                        "12345",
                                        new ArrayList<>())
                        )
                )
        ).isInstanceOf(AbilityParentChildColorDifferentException.class);
    }

    @DisplayName("자식 역량을 추가한다 - 성공")
    @Test
    void addChild() {
        ability.addChild(new Ability2(
                "자식 역량",
                "역량입니다",
                "1234",
                new ArrayList<>()
        ));

        assertThat(ability.getChildren()).hasSize(1);
    }

    @DisplayName("부모 역량과 같은 이름을 가진 자식역량을 추가할 수 없다.")
    @Test
    void addChild_isSameNameWithParent() {
        assertThatThrownBy(() -> ability.addChild(new Ability2(
                "역량",
                "역량입니다",
                "1234",
                new ArrayList<>()
        ))).isInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("부모 역량과 다른 색을 가진 자식역량을 추가할 수 없다.")
    @Test
    void addChild_isSameColorWithParent() {
        assertThatThrownBy(() -> ability.addChild(new Ability2(
                "역량1",
                "역량입니다",
                "12345",
                new ArrayList<>()
        ))).isInstanceOf(AbilityParentChildColorDifferentException.class);
    }

    @DisplayName("Name 객체를 통해 같은 이름인지 비교한다 - 성공")
    @Test
    void isSameName_Name_true() {
        assertThat(ability.isSameName(new Name("역량"))).isTrue();
    }

    @DisplayName("Name 객체를 통해 같은 이름인지 비교한다 - 실패")
    @Test
    void isSameName_Name_false() {
        assertThat(ability.isSameName(new Name("역량2"))).isFalse();
    }

    @DisplayName("Ability 객체를 통해 같은 이름인지 비교한다 - 성공")
    @Test
    void isSameName_Ability_true() {
        assertThat(ability.isSameName(
                new Ability2("역량", "역량입니다", "1234", new ArrayList<>())
        )).isTrue();
    }

    @DisplayName("Ability 객체를 통해 같은 이름인지 비교한다 - 실패")
    @Test
    void isSameName_Ability_false() {
        assertThat(ability.isSameName(
                new Ability2("역량2", "역량입니다", "1234", new ArrayList<>())
        )).isFalse();
    }

    @DisplayName("Color 객체를 통해 같은 색인지 비교한다 - 성공")
    @Test
    void isSameColor_Color_true() {
        assertThat(ability.isSameColor(
                new Color("1234")
        )).isTrue();
    }

    @DisplayName("Color 객체를 통해 같은 색인지 비교한다 - 실패")
    @Test
    void isSameColor_Color_false() {
        assertThat(ability.isSameColor(
                new Color("12345")
        )).isFalse();
    }

    @DisplayName("Ability 객체를 통해 같은 색인지 비교한다 - 성공")
    @Test
    void isSameColor_Ability_true() {
        assertThat(ability.isSameColor(
                new Ability2("역량2", "역량입니다", "1234", new ArrayList<>())
        )).isTrue();
    }

    @DisplayName("Ability 객체를 통해 같은 색인지 비교한다 - 실패")
    @Test
    void isSameColor_Ability_false() {
        assertThat(ability.isSameColor(
                new Ability2("역량2", "역량입니다", "12345", new ArrayList<>())
        )).isFalse();
    }
}
