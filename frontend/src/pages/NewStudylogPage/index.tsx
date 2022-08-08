/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { useState, ChangeEventHandler, FormEventHandler, useRef } from 'react';
import { MainContentStyle } from '../../PageRouter';

import { ERROR_MESSAGE, ALERT_MESSAGE, PATH } from '../../constants';

import { StudylogForm } from '../../models/Studylogs';
import { useMutation } from 'react-query';
import { getLocalStorageItem } from '../../utils/localStorage';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';
import { SUCCESS_MESSAGE } from '../../constants/message';
import { useHistory } from 'react-router-dom';
import { requestPostStudylog } from '../../apis/studylogs';
import StudylogEditor from '../../components/Editor/StudylogEditor';

interface NewStudylogForm extends StudylogForm {
  abilities: number[];
}

const NewStudylogPage = () => {
  const history = useHistory();

  const editorContentRef = useRef<any>(null);

  const [studylogContent, setStudylogContent] = useState<NewStudylogForm>({
    title: '',
    content: '',
    missionId: null,
    sessionId: null,
    tags: [],
    abilities: [],
  });

  const onSelectAbilities = (abilities: number[]) => {
    setStudylogContent({ ...studylogContent, abilities });
  };

  const onChangeTitle: ChangeEventHandler<HTMLInputElement> = (event) => {
    setStudylogContent({ ...studylogContent, title: event.target.value });
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

  const onSelectMission = (mission: { value: string; label: string }) =>
    setStudylogContent({ ...studylogContent, missionId: Number(mission.value) });

  const onSelectSession = (session: { value: string; label: string }) => {
    setStudylogContent({ ...studylogContent, sessionId: Number(session.value) });
  };

  const onCreateStudylog: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (studylogContent.title.length === 0) {
      alert(ALERT_MESSAGE.NO_TITLE);
      return;
    }

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    // FIXME: 한 타임 밀림
    createStudylogRequest({
      ...studylogContent,
      content,
    });
  };

  const { mutate: createStudylogRequest } = useMutation(
    (data: StudylogForm) =>
      requestPostStudylog({
        accessToken: getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN),
        data,
      }),
    {
      onSuccess: async (data) => {
        alert(SUCCESS_MESSAGE.CREATE_POST);
        history.push(PATH.STUDYLOG);
      },
      onError: (error: { code: number; message: string }) => {
        console.log(error);
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  return (
    <div
      css={[
        MainContentStyle,
        css`
          padding: 1rem 0 10rem;
        `,
      ]}
    >
      <StudylogEditor
        title={studylogContent.title}
        contentRef={editorContentRef}
        content={''}
        selectedMissionId={studylogContent.missionId}
        selectedSessionId={studylogContent.sessionId}
        selectedTags={studylogContent.tags}
        selectedAbilities={studylogContent.abilities}
        onChangeTitle={onChangeTitle}
        onSelectMission={onSelectMission}
        onSelectSession={onSelectSession}
        onSelectTag={onSelectTag}
        onSelectAbilities={onSelectAbilities}
        onSubmit={onCreateStudylog}
      />
    </div>
  );
};

export default NewStudylogPage;
