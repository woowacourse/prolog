import axios from 'axios';
import { useContext } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { BASE_URL } from '../../../configs/environment';
import { UserContext } from '../../../contexts/UserProvider';
import * as Styled from './SelectAbilityBox.styles';

/**
 * 역량을 선택할 수 있다.
 * 역량은 자식역량만 선택할 수 있다.
 */
const SelectAbilityBox = ({
  selectAbilityBoxRef, //
  studylog,
  abilities,
  wholeAbility,
}) => {
  const { user } = useContext(UserContext);
  const queryClient = useQueryClient();

  const mappingAbility = useMutation(
    ({ studylogId, abilities }) => {
      const { data } = axios({
        method: 'put',
        url: `${BASE_URL}/studylogs/${studylogId}/abilities`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
        data: {
          abilities,
        },
      });

      return { ...data };
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries([`${user.username}-ability-studylogs`]);
      },
    }
  );

  // TODO : 역량이 추가만 되고 있고, 삭제가 안되고 있다.
  const toggleAbility = ({ studylogId, abilitieIds, targetAblityId }) => {
    const targetIndex = abilitieIds.findIndex((id) => id === targetAblityId);
    // mappingAbility.mutate({ studylogId, abilities: [targetAblityId] });

    // if (targetIndex === -1) {
    //   const newAbilities = [...abilitieIds, targetAblityId];
    //   mappingAbility.mutate({ studylogId, abilities: newAbilities });
    // } else {
    //   const deletedAbilities = [
    //     ...abilitieIds.slice(0, targetIndex),
    //     abilitieIds.slice(targetIndex + 1),
    //   ];
    //   console.log(deletedAbilities);
    //   mappingAbility.mutate({ studylogId, abilities: deletedAbilities });
    // }
  };

  const isChecked = (abilities, targetAbilityId) => {
    return abilities.find((ability) => ability.id === targetAbilityId);
  };

  return (
    <Styled.Wrapper ref={selectAbilityBoxRef}>
      <Styled.Header>
        <p id="selectBox-title">학습로그에 매핑될 역량을 선택해주세요.</p>
        <span className="ability-title">학습로그: {studylog.title}</span>
      </Styled.Header>

      <Styled.AbilityList>
        {wholeAbility?.map((ability) => (
          <Styled.Ability key={ability.id}>
            <label>
              <input
                type="checkbox"
                // value={isChecked(abilities, ability.id)}
                onChange={() =>
                  toggleAbility({
                    studylogId: studylog.id, //
                    abilitieIds: abilities.map((ability) => ability.id),
                    targetAblityId: ability.id,
                  })
                }
                defaultChecked={isChecked(abilities, ability.id)}
                // checked={isChecked(abilities, ability.id)}
              />
              <Styled.ColorCircle backgroundColor={ability.color} />
              <Styled.AbilityName>{ability.name}</Styled.AbilityName>
            </label>
          </Styled.Ability>
        ))}
      </Styled.AbilityList>
    </Styled.Wrapper>
  );
};

export default SelectAbilityBox;
