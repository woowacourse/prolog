/** @jsxImportSource @emotion/react */

import CreatableSelectBox from '../CreatableSelectBox/CreatableSelectBox';
import { COLOR } from '../../enumerations/color';
import SelectBox from '../Controls/SelectBox';
import { PLACEHOLDER } from '../../constants';
import { Mission, Session, Tag } from '../../models/Studylogs';
import styled from '@emotion/styled';
import { useGetMySessions, useMissions, useTags } from '../../hooks/queries/filters';
import { getRowGapStyle } from '../../styles/layout.styles';
import { useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';
import { css } from '@emotion/react';

interface SidebarProps {
  selectedSessionId: Session['sessionId'] | null;
  selectedMissionId: Mission['id'] | null;
  selectedTagList: Tag[];
  onSelectSession: (session: { value: string; label: string }) => void;
  onSelectMission: (mission: { value: string; label: string }) => void;
  onSelectTag: (tags: Tag[], actionMeta: { option: { label: string } }) => void;
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
                   onSelectMission,
                   onSelectSession,
                   onSelectTag,
                 }: SidebarProps) => {
  const { data: missions = [] } = useMissions();
  const { data: tags = [] } = useTags();
  const { data: sessions = [] } = useGetMySessions();
  const {
    user: { username },
  } = useContext(UserContext);
  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));
  const missionOptions = missions.map(({ id, name, session }) => ({
    value: `${id}`,
    label: `[${session?.name}] ${name}`,
  }));
  const sessionOptions = sessions.map(({ sessionId, name }) => ({
    value: `${sessionId}`,
    label: `${name}`,
  }));

  const selectedSession = sessions.find(({ sessionId }) => sessionId === selectedSessionId);
  const selectedMission = missions.find(({ id }) => id === selectedMissionId);

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
                  ? { value: `${selectedSession.sessionId}`, label: selectedSession?.name }
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
      </ul>
    </SidebarWrapper>
  );
};

export default Sidebar;
