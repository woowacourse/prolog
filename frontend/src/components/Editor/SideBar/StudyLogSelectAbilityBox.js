import { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../../../contexts/UserProvider';
import * as Styled from './StudyLogSelectAbilityBox.styles';

/**
 * 역량을 선택할 수 있다.
 * 역량은 자식역량만 선택할 수 있다.
 */
const StudyLogSelectAbilityBox = ({
  setIsSelectAbilityBoxOpen,
  selectedAbilities,
  wholeAbility,
  onSelectAbilities,
}) => {
  const {
    user: { username },
  } = useContext(UserContext);

  const [updatedAbilities, setUpdatedAbilities] = useState(selectedAbilities);

  const [searchTerm, setSearchTerm] = useState('');

  const onChangeSearchInput = (e) => {
    setSearchTerm(e.target.value);
  };

  const filteredAbilities = wholeAbility.filter((abilityObj) => {
    const abilityName = abilityObj.name.trim().toLowerCase();
    const refinedSearchTerm = searchTerm.trim().toLowerCase();
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
    return updatedAbilities.includes(targetAbilityId);
  };

  const onClickSelectButton = () => {
    onSelectAbilities(updatedAbilities);
    setIsSelectAbilityBoxOpen(false);
  };

  const onClickCloseButton = () => {
    setIsSelectAbilityBoxOpen(false);
  };

  return (
    <Styled.Wrapper>
      <Styled.Header>
        <p id="selectBox-title">학습로그에 매핑될 역량을 선택해주세요.</p>
        <span className="ability-title">📢 역량은 하위역량만 선택가능합니다.</span>
        <span className="ability-link">
          <Link to={`/${username}/ability`} target="_blank">
            역량 관리 페이지 이동
          </Link>
        </span>
        <Styled.CloseButton onClick={onClickCloseButton}></Styled.CloseButton>
        <Styled.SearchInput
          type="search"
          placeholder="역량 검색"
          value={searchTerm}
          onChange={onChangeSearchInput}
          autoFocus
        />
      </Styled.Header>

      <Styled.AbilityList>
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
        <button onClick={onClickSelectButton} type="button">
          역량등록
        </button>
      </Styled.Footer>
    </Styled.Wrapper>
  );
};

export default StudyLogSelectAbilityBox;
