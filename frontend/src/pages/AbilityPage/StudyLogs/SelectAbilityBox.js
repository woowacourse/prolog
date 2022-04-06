import { useState } from 'react';
import * as Styled from './SelectAbilityBox.styles';

/**
 * 역량을 선택할 수 있다.
 * 역량은 자식역량만 선택할 수 있다.
 */
const SelectAbilityBox = ({
  selectAbilityBoxRef,
  mappingAbility,
  studylogId,
  abilities,
  wholeAbility,
  setSelectAbilityBox,
  refetch,
}) => {
  const abilityIds = abilities.map((ability) => ability.id);
  const [updatedAbilites, setUpdatedAbilities] = useState(abilityIds);

  const onSelectAbility = (event) => {
    const targetAbilityId = Number(event.target.id);
    const currAbilities = new Set(updatedAbilites);

    if (currAbilities.has(targetAbilityId)) {
      currAbilities.delete(targetAbilityId);
    } else {
      currAbilities.add(targetAbilityId);
    }

    setUpdatedAbilities([...currAbilities]);
  };

  const onMappingAbility = () => {
    mappingAbility.mutate({ studylogId, abilities: updatedAbilites });
    if (mappingAbility.isSuccess) {
      refetch();
    }

    setSelectAbilityBox({ id: studylogId, isOpen: false });
  };

  const isChecked = (abilityIds, targetAbilityId) => {
    return abilityIds.find((id) => id === targetAbilityId);
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
                  id={ability.id}
                  type="checkbox"
                  onClick={onSelectAbility}
                  defaultChecked={isChecked(abilityIds, ability.id)}
                />
                <Styled.ColorCircle backgroundColor={ability.color} />
                <Styled.AbilityName>{ability.name}</Styled.AbilityName>
              </label>
            </Styled.Ability>
          ))
        )}
      </Styled.AbilityList>

      <Styled.Footer>
        <button onClick={onMappingAbility}>역량등록</button>
      </Styled.Footer>
    </Styled.Wrapper>
  );
};

export default SelectAbilityBox;
