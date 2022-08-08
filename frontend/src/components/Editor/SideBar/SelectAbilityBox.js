import { useState } from 'react';
import * as Styled from './SelectAbilityBox.styles';

/**
 * 역량을 선택할 수 있다.
 * 역량은 자식역량만 선택할 수 있다.
 */
const SelectAbilityBox = ({
  setIsSelectAbilityBoxOpen,
  selectedAbilities,
  wholeAbility,
  onSelectAbilities,
}) => {
  const [updatedAbilities, setUpdatedAbilities] = useState(selectedAbilities);

  const onClickAbility = (event) => {
    const targetAbilityId = Number(event.target.id);
    const currAbilities = new Set(updatedAbilities);

    if (currAbilities.has(targetAbilityId)) {
      currAbilities.delete(targetAbilityId);
    } else {
      currAbilities.add(targetAbilityId);
    }

    setUpdatedAbilities([...currAbilities]);
  };

  const isChecked = (targetAbilityId) => {
    return updatedAbilities.find((id) => id === targetAbilityId);
  };

  const onClickSelectButton = () => {
    onSelectAbilities(updatedAbilities);
    setIsSelectAbilityBoxOpen(false);
  };

  return (
    <Styled.Wrapper>
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
                  id={ability.id}
                  type="checkbox"
                  onClick={onClickAbility}
                  defaultChecked={isChecked(ability.id)}
                />
                <Styled.ColorCircle backgroundColor={ability.color} />
                <Styled.AbilityName>{ability.name}</Styled.AbilityName>
              </label>
            </Styled.Ability>
          ))
        )}
      </Styled.AbilityList>

      <Styled.Footer>
        <button onClick={onClickSelectButton}>역량등록</button>
      </Styled.Footer>
    </Styled.Wrapper>
  );
};

export default SelectAbilityBox;
