/** @jsxImportSource @emotion/react */

import CreatableSelectBox from '../CreatableSelectBox/CreatableSelectBox';
import { COLOR } from '../../enumerations/color';
import SelectBox from '../Controls/SelectBox';
import { ERROR_MESSAGE, PLACEHOLDER } from '../../constants';
import { Mission, Session, Tag } from '../../models/Studylogs';
import styled from '@emotion/styled';
import { useMissions, useSessions, useTags } from '../../hooks/queries/filters';
import { getRowGapStyle } from '../../styles/layout.styles';
import StudyLogSelectAbilityBox from './SideBar/StudyLogSelectAbilityBox';
import { useQuery } from 'react-query';
import AbilityRequest, { ErrorData } from '../../apis/ability';
import { useContext, useState } from 'react';
import { UserContext } from '../../contexts/UserProvider';
import Chip from '../Chip/Chip';
import { Button } from '../../components';
import FlexBox from '../@shared/FlexBox/FlexBox';
import { css } from '@emotion/react';

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
const AbilitySelectList = styled.li`
  position: relative;
`;
const AbilityList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 4px 3px;
`;

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

const FlexGap = css`
  gap: 0.5rem;
`;

const PlusButton = css`
  background-color: ${COLOR.LIGHT_GRAY_100};
  font-weight: bold;
  color: ${COLOR.LIGHT_GRAY_600};
  width: 22px;
  height: 22px;
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

  /** 선택된 역량을 보여준다.*/
  const SelectedAbilityChips = ({ selectedAbilityIds }) => {
    const selectedAbilities = wholeAbility.filter(({ id }) => selectedAbilityIds.includes(id));

    return (
      <AbilityList>
        {selectedAbilities?.map((ability) => {
          return (
            <li key={ability.id}>
              {
                <Chip
                  backgroundColor={ability.color}
                  border={`1px solid ${COLOR.BLACK_OPACITY_300}`}
                  fontSize="1.2rem"
                  lineHeight="1.5"
                  marginRight="0"
                  maxWidth="21.9rem"
                  onDelete={() => {
                    onSelectAbilities(selectedAbilityIds.filter((id) => id !== ability.id));
                  }}
                >
                  {ability.name}
                </Chip>
              }
            </li>
          );
        })}
      </AbilityList>
    );
  };

  return (
    <SidebarWrapper>
      <ul
        css={[
          getRowGapStyle('1.6rem'),
          css`
            display: flex;
            flex-direction: column;
          `,
        ]}
      >
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
          <div>
            <CreatableSelectBox
              options={tagOptions}
              placeholder={PLACEHOLDER.TAG}
              onChange={onSelectTag}
              value={selectedTagList.map(({ name }) => ({ value: name, label: `#${name}` }))}
            />
          </div>
        </li>

          <AbilitySelectList>
            <FilterTitle>
              <FlexBox css={FlexGap} alignItems="center">
                abilities
                <Button
                  size="XX_SMALL"
                  type="button"
                  cssProps={PlusButton}
                  onClick={() => setIsSelectAbilityBoxOpen(true)}
                >
                  +
                </Button>
              </FlexBox>
            </FilterTitle>

            <SelectedAbilityChips selectedAbilityIds={selectedAbilities} />
            {isSelectAbilityBoxOpen && (
              <StudyLogSelectAbilityBox
                setIsSelectAbilityBoxOpen={setIsSelectAbilityBoxOpen}
                selectedAbilities={selectedAbilities}
                wholeAbility={wholeAbility}
                onSelectAbilities={onSelectAbilities}
              />
            )}
          </AbilitySelectList>
      
      </ul>
    </SidebarWrapper>
  );
};

export default Sidebar;
