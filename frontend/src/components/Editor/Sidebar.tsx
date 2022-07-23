/** @jsxImportSource @emotion/react */

import CreatableSelectBox from '../CreatableSelectBox/CreatableSelectBox';
import { COLOR } from '../../enumerations/color';
import SelectBox from '../Controls/SelectBox';
import { ERROR_MESSAGE, PLACEHOLDER } from '../../constants';
import { Mission, Session, Tag } from '../../models/Studylogs';
import styled from '@emotion/styled';
import { useMissions, useSessions, useTags } from '../../hooks/queries/filters';
import { getRowGapStyle } from '../../styles/layout.styles';
import SelectAbilityBox from './SideBar/SelectAbilityBox';
import { useQuery } from 'react-query';
import AbilityRequest, { ErrorData } from '../../apis/ability';
import { useContext, useState } from 'react';
import { UserContext } from '../../contexts/UserProvider';
import Chip from '../Chip/Chip';
import { Button } from '../../components';
<<<<<<< HEAD
import FlexBox from '../@shared/FlexBox/FlexBox';
=======
>>>>>>> 8203dce44b9742a50681cd6e58e46956f8f8730a

interface SidebarProps {
  selectedSessionId: Session['id'] | null;
  selectedMissionId: Mission['id'] | null;
  selectedTagList: Tag[];
  selectedAbilities: number[];
  onSelectSession: (session: { value: string; label: string }) => void;
  onSelectMission: (mission: { value: string; label: string }) => void;
  onSelectTag: (tags: Tag[], actionMeta: { option: { label: string } }) => void;
  onSelectAbilities: (abilities: number[]) => void;
}

const SidebarWrapper = styled.aside`
  width: 24rem;
  padding: 1rem;

  background-color: white;
  border: 1px solid ${COLOR.LIGHT_GRAY_100};
  border-radius: 2rem;
`;

const FilterTitle = styled.h3`
  margin-bottom: 10px;
  padding-bottom: 2px;

  border-bottom: 1px solid ${COLOR.DARK_GRAY_500};

  font-size: 1.8rem;
  font-weight: bold;
  line-height: 1.5;
`;

const Sidebar = ({
  selectedSessionId,
  selectedMissionId,
  selectedTagList,
  selectedAbilities,
  onSelectMission,
  onSelectSession,
  onSelectTag,
  onSelectAbilities,
}: SidebarProps) => {
  const [isSelectAbilityBoxOpen, setIsSelectAbilityBoxOpen] = useState(false);
  const { data: missions = [] } = useMissions();
  const { data: tags = [] } = useTags();
  const { data: sessions = [] } = useSessions();
  const {
    user: { username },
  } = useContext(UserContext);

  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));
  const missionOptions = missions.map(({ id, name, session }) => ({
    value: `${id}`,
    label: `[${session?.name}] ${name}`,
  }));
  const sessionOptions = sessions.map(({ id, name }) => ({ value: `${id}`, label: `${name}` }));

  const selectedSession = sessions.find(({ id }) => id === selectedSessionId);
  const selectedMission = missions.find(({ id }) => id === selectedMissionId);

  /** 전체 역량 조회 */
  const { data: abilities = [] } = useQuery(
    [`${username}-abilities`],
    () => AbilityRequest.getAbilityList({ url: `/members/${username}/abilities` }),
    {
      onError: (errorData: ErrorData) => {
        const errorCode = errorData?.code;

        alert(ERROR_MESSAGE[errorCode] ?? '역량을 가져오는데 실패하였습니다. 다시 시도해주세요.');
      },
    }
  );

  const wholeAbility = abilities?.map((parentAbility) => [...parentAbility.children]).flat();

  console.log('wholeAbility', wholeAbility);

  /** 선택된 역량을 보여준다.*/
  const SelectedAbilityChips = ({ selectedAbilityIds }) => {
    const selectedAbilities = wholeAbility.filter(({ id }) => selectedAbilityIds.includes(id));
    return (
      <ul id="mapped-abilities-list">
        {selectedAbilities?.map((ability) => {
          return (
            <li key={ability.id}>
              {
                <Chip
                  backgroundColor={ability.color}
                  border={`1px solid ${COLOR.BLACK_OPACITY_300}`}
                  fontSize="1.2rem"
                  lineHeight="1.5"
                  onDelete={() => {
                    console.log('ability.id', ability.id);
                    // TODO: 역량 삭제
                  }}
                >
                  {ability.name}
                </Chip>
              }
            </li>
          );
        })}
      </ul>
    );
  };

  return (
    <SidebarWrapper>
      <ul css={[getRowGapStyle('1.6rem')]}>
        <li>
          <FilterTitle>session</FilterTitle>
          <div>
            <SelectBox
              options={sessionOptions}
              placeholder="+ 세션을 선택하세요."
              onChange={onSelectSession}
              value={
                selectedSession
                  ? { value: `${selectedSession.id}`, label: selectedSession?.name }
                  : undefined
              }
            />
          </div>
        </li>
        <li>
          <FilterTitle>mission</FilterTitle>
          <div>
            <SelectBox
              options={missionOptions}
              placeholder="+ 미션을 선택하세요."
              onChange={onSelectMission}
              selectedSessionId={selectedSessionId?.toString()}
              value={
                selectedMission
                  ? { value: `${selectedMission?.id}`, label: selectedMission?.name }
                  : undefined
              }
            />
          </div>
        </li>
        <li>
          <FilterTitle>tags</FilterTitle>
          <CreatableSelectBox
            options={tagOptions}
            placeholder={PLACEHOLDER.TAG}
            onChange={onSelectTag}
            value={selectedTagList.map(({ name }) => ({ value: name, label: `#${name}` }))}
          />
        </li>
        <li>

          <FilterTitle>
            <FlexBox>
              abilities
              <Button
                size="XX_SMALL"
                type="button"
                css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
                onClick={() => setIsSelectAbilityBoxOpen(true)}
              >
                +
              </Button>
            </FlexBox>
          </FilterTitle>


          <SelectedAbilityChips selectedAbilityIds={selectedAbilities} />
          {isSelectAbilityBoxOpen && (
            <SelectAbilityBox
              setIsSelectAbilityBoxOpen={setIsSelectAbilityBoxOpen}
              selectedAbilities={selectedAbilities}
              wholeAbility={wholeAbility}
              onSelectAbilities={onSelectAbilities}
            ></SelectAbilityBox>
          )}
        </li>
      </ul>
    </SidebarWrapper>
  );
};

export default Sidebar;
