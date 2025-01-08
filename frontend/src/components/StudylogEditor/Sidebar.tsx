/** @jsxImportSource @emotion/react */

import CreatableSelectBox from '../CreatableSelectBox/CreatableSelectBox';
import { COLOR } from '../../enumerations/color';
import SelectBox from '../Controls/SelectBox';
import { ERROR_MESSAGE, PLACEHOLDER } from '../../constants';
import { Answer, Mission, Question, Session, StudylogForm, Tag } from '../../models/Studylogs';
import styled from '@emotion/styled';
import { useGetMySessions, useMissions, useTags } from '../../hooks/queries/filters';
import { getRowGapStyle } from '../../styles/layout.styles';
import { MutableRefObject, useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';
import { css } from '@emotion/react';
import { fetchQuestionsByMissionId } from '../../apis/questions';

interface SidebarProps {
  mode: 'create' | 'edit';
  questions: Question[];
  setQuestions: React.Dispatch<React.SetStateAction<Question[]>>;
  editorAnswersRef: MutableRefObject<Answer[]>;
  studylogContent: StudylogForm;
  setStudylogContent: React.Dispatch<React.SetStateAction<StudylogForm>>;
}

const SidebarWrapper = styled.aside`
  width: 24rem;
  height: 100%;
  padding: 2rem;
`;

const FilterTitle = styled.h3`
  margin-bottom: 10px;
  padding-bottom: 2px;
  line-height: 1.5;
  font-weight: 600;
  font-size: 1.5rem;
  color: ${COLOR.DARK_GRAY_600};
`;

type SelectOption = { value: string; label: string };

const Sidebar = ({
  mode,
  questions,
  setQuestions,
  editorAnswersRef,
  studylogContent,
  setStudylogContent,
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
    label: `${name}`,
  }));
  const sessionOptions = sessions.map(({ sessionId, name }) => ({
    value: `${sessionId}`,
    label: `${name}`,
  }));

  const selectedSession = sessions.find(({ sessionId }) => sessionId === studylogContent.sessionId);
  const selectedMission = missions.find(({ id }) => id === studylogContent.missionId);

  const onSelectMission = async (mission: SelectOption | null) => {
    if (mode === 'edit') {
      alert('수정화면에서는 미션을 수정할 수 없습니다.');
      return;
    }

    if (questions.length > 0) {
      const confirmed = window.confirm('입력한 답변이 초기화 됩니다. 계속 진행하시겠습니까?');
      if (!confirmed) return;
    }

    if (!mission) {
      editorAnswersRef.current = []; // 기존 답변 초기화
      setStudylogContent((prev) => ({ ...prev, missionId: null, answers: [] }));
      setQuestions([]);
      return;
    }

    setStudylogContent((prev) => ({ ...prev, missionId: Number(mission.value) }));

    try {
      const response = await fetchQuestionsByMissionId(Number(mission.value));
      const { questions: fetchedQuestions } = response.data;

      setQuestions(fetchedQuestions);

      editorAnswersRef.current = fetchedQuestions.map((question) => ({
        questionId: question.id,
        answerContent: '',
      }));
    } catch (error) {
      console.error('Failed to fetch questions:', error);
      alert(ERROR_MESSAGE.DEFAULT);
    }
  };

  const onSelectSession = (session: SelectOption) => {
    if (mode === 'edit') {
      alert('수정화면에서는 세션을 수정할 수 없습니다.');
      return;
    }

    setStudylogContent({ ...studylogContent, sessionId: Number(session.value) });
  };

  const onSelectTag = (tags, actionMeta) => {
    if (actionMeta.action === 'create-option') {
      actionMeta.option.label = '#' + actionMeta.option.label;
    }

    setStudylogContent({
      ...studylogContent,
      tags: tags.map(({ value }) => ({ name: value.replace(/#/, '') })),
    });
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
        <li
          css={[
            css`
              margin-bottom: 1.6rem;
            `,
          ]}
        >
          <FilterTitle>Session</FilterTitle>
          <div>
            <SelectBox
              options={sessionOptions}
              placeholder="강의를 선택하세요."
              onChange={onSelectSession}
              value={
                selectedSession
                  ? {
                      value: `${selectedSession.sessionId}`,
                      label: selectedSession?.name,
                    }
                  : undefined
              }
              editable={mode === 'create'}
            />
          </div>
        </li>
        <li
          css={[
            css`
              margin-bottom: 1.6rem;
            `,
          ]}
        >
          <FilterTitle>Mission</FilterTitle>
          <div>
            <SelectBox
              options={missionOptions}
              placeholder="미션을 선택하세요."
              onChange={onSelectMission}
              selectedSessionId={studylogContent.sessionId?.toString()}
              value={
                selectedMission
                  ? { value: `${selectedMission?.id}`, label: selectedMission?.name }
                  : undefined
              }
              editable={mode === 'create'}
            />
          </div>
        </li>
        <li>
          <FilterTitle>Tag</FilterTitle>
          <div>
            <CreatableSelectBox
              options={tagOptions}
              placeholder={PLACEHOLDER.TAG}
              onChange={onSelectTag}
              value={studylogContent.tags.map(({ name }) => ({ value: name, label: `#${name}` }))}
            />
          </div>
        </li>
      </ul>
    </SidebarWrapper>
  );
};

export default Sidebar;
