import { useState } from 'react';
import * as Styled from './NewSelectAbilityBox.styles';

/**
 * 역량을 선택할 수 있다.
 * 역량은 자식역량만 선택할 수 있다.
 */
const NewSelectAbilityBox = ({
  setIsSelectAbilityBoxOpen,
  selectedAbilities,
  wholeAbility,
  onSelectAbilities,
}) => {
  const [updatedAbilities, setUpdatedAbilities] = useState(selectedAbilities);

  const [searchTerm, setSearchTerm] = useState('');

  const handleChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const filteredAbilities = wholeAbility.filter((abilityObj) => {
    const abilityName = abilityObj.name.replace(/(\s*)/g, '').toLowerCase();
    const refinedSearchTerm = searchTerm.replace(/(\s*)/g, '').toLowerCase();
    return abilityName.includes(refinedSearchTerm);
  });

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
        <Styled.CloseButton onClick={() => setIsSelectAbilityBoxOpen(false)}></Styled.CloseButton>
      </Styled.Header>

      <Styled.AbilityList>
        <Styled.SearchInput
          type="search"
          placeholder="역량 검색"
          value={searchTerm}
          onChange={handleChange}
          autoFocus
        />
        {wholeAbility.length === 0 ? (
          <Styled.EmptyAbilityGuide>등록된 역량이 없습니다.</Styled.EmptyAbilityGuide>
        ) : (
          filteredAbilities?.map((ability) => (
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

export default NewSelectAbilityBox;
