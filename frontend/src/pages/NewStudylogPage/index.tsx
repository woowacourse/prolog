/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { useState, ChangeEventHandler, FormEventHandler, useRef, useContext } from 'react';
import { MainContentStyle } from '../../PageRouter';

import { ERROR_MESSAGE, ALERT_MESSAGE, PATH } from '../../constants';

import { StudylogForm, TempSavedStudyLogForm, TempSavedStudyLog } from '../../models/Studylogs';
import { useMutation, useQuery, UseQueryResult } from 'react-query';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';
import { CONFIRM_MESSAGE, SUCCESS_MESSAGE } from '../../constants/message';
import { useHistory } from 'react-router-dom';
import {
  requestPostTempSavedStudylog,
  requestPostStudylog,
  requestGetTempSavedStudylog,
} from '../../apis/studylogs';
import StudylogEditor from '../../components/Editor/StudylogEditor';
import useBeforeunload from '../../hooks/useBeforeunload';
import { ResponseError } from '../../apis/studylogs';
import { AxiosError, AxiosResponse } from 'axios';
import REACT_QUERY_KEY from '../../constants/reactQueryKey';
import { UserContext } from '../../contexts/UserProvider';

interface NewStudylogForm extends StudylogForm {
  abilities: number[];
}

type SelectOption = { value: string; label: string };

const NewStudylogPage = () => {
  const history = useHistory();
  const editorContentRef = useRef<any>(null);
  const { user } = useContext(UserContext);
  const { username } = user;

  useBeforeunload(editorContentRef);

  const [studylogContent, setStudylogContent] = useState<NewStudylogForm>({
    title: '',
    content: null,
    missionId: null,
    sessionId: null,
    tags: [],
    abilities: [],
  });

  const fetchTempSavedStudylogRequest: UseQueryResult<
    AxiosResponse<TempSavedStudyLog>,
    AxiosError
  > = useQuery(
    [REACT_QUERY_KEY.TEMP_STUDYLOG, username],
    () =>
      requestGetTempSavedStudylog({
        accessToken: localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN) as string,
      }),
    {
      refetchOnWindowFocus: false,
      onSuccess: ({ data }) => {
        const isTempSavedStudylogExist = Object.keys(data).some((key) => data[key] !== null);
        if (isTempSavedStudylogExist) {
          setStudylogContent({
            title: data.title,
            content: data.content,
            missionId: data.mission?.id ?? null,
            sessionId: data.session?.id ?? null,
            tags: data.tags,
            abilities: data.abilities.map(({ id }) => id),
          });

          return;
        }

        setStudylogContent({ ...studylogContent, content: '' });
      },
    }
  );

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

  const onSelectMission = (mission: SelectOption) =>
    setStudylogContent({ ...studylogContent, missionId: Number(mission.value) });

  const onSelectSession = (session: SelectOption) => {
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

  const onTempSaveStudylog = () => {
    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (studylogContent.title.length === 0 && content.length === 0) {
      alert(ALERT_MESSAGE.NO_TITLE_OR_CONTENT);

      return;
    }

    if (window.confirm(CONFIRM_MESSAGE.TEMP_SAVE_STUDYLOG)) {
      tempSaveStudylogRequest({
        ...studylogContent,
        content,
      });
    }
  };

  const { mutate: createStudylogRequest } = useMutation(
    (data: StudylogForm) =>
      requestPostStudylog({
        accessToken: localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN) as string,
        data,
      }),
    {
      onSuccess: async () => {
        alert(SUCCESS_MESSAGE.CREATE_POST);
        history.push(PATH.STUDYLOG);
      },
      onError: (error: ResponseError) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const { mutate: tempSaveStudylogRequest } = useMutation(
    (data: TempSavedStudyLogForm) =>
      requestPostTempSavedStudylog({
        accessToken: localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN) as string,
        data,
      }),
    {
      onSuccess: () => {
        alert(SUCCESS_MESSAGE.TEMP_SAVE_POST);
      },
      onError: () => {
        alert(ERROR_MESSAGE.DEFAULT);
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
        content={studylogContent.content}
        contentRef={editorContentRef}
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
        onTempSave={onTempSaveStudylog}
      />
    </div>
  );
};

export default NewStudylogPage;
