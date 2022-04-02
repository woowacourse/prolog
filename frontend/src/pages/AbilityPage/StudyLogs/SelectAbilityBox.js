import * as Styled from './SelectAbilityBox.styles';

/**
 * 역량을 선택할 수 있다.
 * 역량은 자식역량만 선택할 수 있다.
 */
const SelectAbilityBox = ({
  selectAbilityBoxRef,
  toggleAbility,
  studylog,
  abilities,
  wholeAbility,
}) => {
  const isChecked = (abilities, targetAbilityId) => {
    return abilities.find((ability) => ability.id === targetAbilityId);
  };

  return (
    <Styled.Wrapper ref={selectAbilityBoxRef}>
      <Styled.Header>
        <p id="selectBox-title">학습로그에 매핑될 역량을 선택해주세요.</p>
        <span className="ability-title">📢 역량은 하위역량만 선택가능합니다.</span>
      </Styled.Header>

      <Styled.AbilityList>
        {wholeAbility.length === 0 ? (
          <Styled.EmptyAbilityGuide>등록된 역량이 없습니다.</Styled.EmptyAbilityGuide>
        ) : (
          wholeAbility?.map((ability) => (
            <Styled.Ability key={ability.id}>
              <label>
                <input
                  type="checkbox"
                  onChange={() =>
                    toggleAbility({
                      studylogId: studylog.id, //
                      abilitieIds: abilities.map((ability) => ability.id),
                      targetAblityId: ability.id,
                    })
                  }
                  defaultChecked={isChecked(abilities, ability.id)}
                />
                <Styled.ColorCircle backgroundColor={ability.color} />
                <Styled.AbilityName>{ability.name}</Styled.AbilityName>
              </label>
            </Styled.Ability>
          ))
        )}
      </Styled.AbilityList>
    </Styled.Wrapper>
  );
};

export default SelectAbilityBox;
