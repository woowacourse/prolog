/** @jsxImportSource @emotion/react */

import {
  ChangeEventHandler,
  FormEventHandler,
  useContext,
  useEffect,
  useRef,
  useState,
} from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { ALERT_MESSAGE, ERROR_MESSAGE, PATH } from '../../constants';

import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';

import StudylogEditor from '../../components/Editor/StudylogEditor';
import { Studylog, StudylogForm } from '../../models/Studylogs';
import { useMutation, useQuery, UseQueryResult } from 'react-query';
import REACT_QUERY_KEY from '../../constants/reactQueryKey';
import { requestEditStudylog, requestGetStudylog } from '../../apis/studylogs';
import { AxiosError, AxiosResponse } from 'axios';
import { getLocalStorageItem } from '../../utils/localStorage';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';
import { SUCCESS_MESSAGE } from '../../constants/message';
import { ResponseError } from '../../apis/studylogs';

type SelectOption = { value: string; label: string };

// 나중에 학습로그 작성 페이지와 같아질 수  있음(임시저장)
const EditStudylogPage = () => {
  const history = useHistory();

  const editorContentRef = useRef<any>(null);

  const [studylogContent, setStudylogContent] = useState<StudylogForm>({
    title: '',
    content: null,
    missionId: null,
    sessionId: null,
    tags: [],
  });

  const { user } = useContext(UserContext);
  const { accessToken, username } = user;

  const { id } = useParams<{ id: string }>();

  const fetchStudylogRequest: UseQueryResult<AxiosResponse<Studylog>, AxiosError> = useQuery(
    [REACT_QUERY_KEY.STUDYLOG, id],
    () => requestGetStudylog({ id, accessToken }),
    {
      onSuccess: ({ data }) => {
        setStudylogContent({
          title: data.title,
          content: data.content,
          missionId: data.mission?.id || null,
          sessionId: data.session?.id || null,
          tags: data.tags,
        });
      },
    }
  );

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

  const onSelectMission = (mission: SelectOption | null) => {
    if (!mission) {
      setStudylogContent({ ...studylogContent, missionId: null });
      return;
    }

    setStudylogContent({ ...studylogContent, missionId: Number(mission.value) });
  };

  const onSelectSession = (session: SelectOption | null) => {
    if (!session) {
      setStudylogContent({ ...studylogContent, sessionId: null });
      return;
    }

    setStudylogContent({ ...studylogContent, sessionId: Number(session.value) });
  };

  const onEditStudylog: FormEventHandler<HTMLFormElement> = (event) => {
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

    editStudylogRequest({
      ...studylogContent,
      content,
    });
  };

  const { mutate: editStudylogRequest } = useMutation(
    (data: StudylogForm) =>
      requestEditStudylog({
        id,
        accessToken: getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN),
        data,
      }),
    {
      onSuccess: async () => {
        alert(SUCCESS_MESSAGE.EDIT_POST);
        history.push(`${PATH.STUDYLOG}/${id}`);
      },

      onError: (error: ResponseError) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.FAIL_TO_EDIT_STUDYLOG);
      },
    }
  );

  const author = fetchStudylogRequest.data?.data.author;

  useEffect(() => {
    if (author && username !== author.username) {
      alert(ALERT_MESSAGE.CANNOT_EDIT_OTHERS);
      history.push(`${PATH.STUDYLOG}/${id}`);
    }
  }, [username, author]);

  useEffect(() => {
    fetchStudylogRequest.refetch();
  }, [id]);

  return (
    <div css={MainContentStyle}>
      <StudylogEditor
        title={studylogContent.title}
        content={studylogContent.content}
        contentRef={editorContentRef}
        selectedMissionId={studylogContent.missionId}
        selectedSessionId={studylogContent.sessionId}
        selectedTags={studylogContent.tags}
        onChangeTitle={onChangeTitle}
        onSelectMission={onSelectMission}
        onSelectSession={onSelectSession}
        onSelectTag={onSelectTag}
        onSubmit={onEditStudylog}
      />
    </div>
  );
};

export default EditStudylogPage;
