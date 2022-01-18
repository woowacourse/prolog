package wooteco.prolog.report.domain.ablity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HistoryTest {

    private History history;

    @BeforeEach
    void setUp() {
        history = new History();
        history.addAbility(new Ability2(
                "역량",
                "역량입니다",
                "1234",
                new ArrayList<>()
        ));
    }

    @DisplayName("동일한 이름의 부모역량은 추가할 수 없다.")
    @Test
    void add_hasSameName() {
        assertThatThrownBy(() -> history.addAbility(new Ability2(
                "역량",
                "역량입니다",
                "12345",
                new ArrayList<>()
        ))).isInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("동일한 색상의 부모역량을 추가할 수 없다.")
    @Test
    void add_hasSameColor() {
        assertThatThrownBy(() -> history.addAbility(new Ability2(
                "역량2",
                "역량입니다",
                "1234",
                new ArrayList<>()
        ))).isInstanceOf(AbilityParentColorDuplicateException.class);
    }
}
