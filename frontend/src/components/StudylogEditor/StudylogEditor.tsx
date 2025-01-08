/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import {
  ChangeEventHandler,
  FormEventHandler,
  useContext,
  useEffect,
  useRef,
  useState,
} from 'react';
import { MainContentStyle } from '../../PageRouter';

import { ALERT_MESSAGE, COLOR, ERROR_MESSAGE, PATH } from '../../constants';

import { Answer, Question, Studylog, StudylogForm } from '../../models/Studylogs';
import { useMutation, useQuery, UseQueryResult } from 'react-query';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';
import { CONFIRM_MESSAGE, SUCCESS_MESSAGE } from '../../constants/message';
import { useHistory, useParams } from 'react-router-dom';
import {
  requestEditStudylog,
  requestGetStudylog,
  requestPostStudylog,
  ResponseError,
} from '../../apis/studylogs';
import useBeforeunload from '../../hooks/useBeforeunload';
import useTempSavedStudylog from '../../hooks/Studylog/useTempSavedStudylog';
import { fetchQuestionsByMissionId } from '../../apis/questions';
import { getFlexStyle } from '../../styles/flex.styles';
import Editor from '../../components/Editor/Editor';
import Sidebar from './Sidebar';
import styled from '@emotion/styled';
import QuestionAnswers from './QuestionAnswers';
import { EditorStyle } from '../../components/Introduction/Introduction.styles';
import { markdownStyle } from '../../styles/markdown.styles';
import { EditorTitleStyle, EditorWrapperStyle } from '../../components/Editor/Editor.styles';
import { Card, SectionName } from './styles';
import { UserContext } from '../../contexts/UserProvider';
import { AxiosError, AxiosResponse } from 'axios';
import REACT_QUERY_KEY from '../../constants/reactQueryKey';

const SubmitButtonStyle = css`
  width: 100%;
  background-color: ${COLOR.LIGHT_BLUE_300};
  padding: 1rem 0;
  border-radius: 1.6rem;

  :hover {
    background-color: ${COLOR.LIGHT_BLUE_500};
  }
`;

const TempSaveButtonStyle = css`
  width: 100%;
  background-color: ${COLOR.LIGHT_GRAY_400};
  padding: 1rem 0;
  border-radius: 1.6rem;

  :hover {
    background-color: ${COLOR.LIGHT_GRAY_600};
  }
`;

const sidebarStyle = css`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: auto;
  gap: 1.5rem;
  margin-left: 1.5rem;
  border-radius: 8px;
  border: 1px solid ${COLOR.LIGHT_GRAY_50};
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: ${COLOR.WHITE};
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
`;

interface StudylogEditorProps {
  mode: 'create' | 'edit';
}

const StudylogEditor = ({ mode }: StudylogEditorProps): JSX.Element => {
  const history = useHistory();
  const editorContentRef = useRef<any>(null);
  const editorAnswersRef = useRef<Answer[]>([]);
  const [questions, setQuestions] = useState<Question[]>([]);

  const {
    tempSavedStudylog,
    createTempSavedStudylog,
    removeCachedTempSavedStudylog,
  } = useTempSavedStudylog();

  useBeforeunload(editorContentRef);

  const [studylogContent, setStudylogContent] = useState<StudylogForm>({
    title: '',
    content: null,
    missionId: null,
    sessionId: null,
    tags: [],
    answers: [],
  });

  const { id } = useParams<{ id: string }>();

  const onChangeTitle: ChangeEventHandler<HTMLInputElement> = (event) => {
    setStudylogContent({ ...studylogContent, title: event.target.value });
  };

  const onCreateStudylog: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    const content = editorContentRef.current?.getInstance().getMarkdown() || '';
    const answers: Answer[] = Object.values(editorAnswersRef.current);

    if (studylogContent.title.length === 0) {
      alert(ALERT_MESSAGE.NO_TITLE);
      return;
    }

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    if (mode === 'edit') {
      editStudylogRequest({
        ...studylogContent,
        content,
        answers,
      });
    } else if (mode === 'create') {
      createStudylogRequest({
        ...studylogContent,
        content,
        answers,
      });
    }
  };

  const fetchQuestions = async (missionId: number, answers: Answer[] | null) => {
    try {
      const response = await fetchQuestionsByMissionId(missionId);
      const { questions: fetchedQuestions } = response.data;

      if (!answers || answers.length === 0) {
        return;
      }

      setQuestions(fetchedQuestions);

      editorAnswersRef.current = answers.map((answer) => ({
        questionId: answer.questionId,
        answerContent: answer.answerContent,
      }));
    } catch (error) {
      console.error('Failed to fetch questions:', error);
      alert(ERROR_MESSAGE.DEFAULT);
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
        removeCachedTempSavedStudylog();
        alert(SUCCESS_MESSAGE.CREATE_POST);
        history.push(PATH.STUDYLOG);
      },
      onError: (error: ResponseError) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const onTempSaveStudylog = () => {
    const content = editorContentRef.current?.getInstance().getMarkdown() || '';
    const answers: Answer[] = editorAnswersRef.current;

    if (studylogContent.title.length === 0 && content.length === 0) {
      alert(ALERT_MESSAGE.NO_TITLE_OR_CONTENT);
      return;
    }

    if (window.confirm(CONFIRM_MESSAGE.TEMP_SAVE_STUDYLOG)) {
      createTempSavedStudylog({
        ...studylogContent,
        content,
        answers,
      });
    }
  };

  const fetchStudylogRequest: UseQueryResult<AxiosResponse<Studylog>, AxiosError> = useQuery(
    [REACT_QUERY_KEY.STUDYLOG, id],
    () => requestGetStudylog({ id, accessToken }),
    {
      onSuccess: ({ data }) => {
        setStudylogContent({
          title: data.title,
          content: data.content,
          missionId: data.mission?.id || null,
          sessionId: data.session?.sessionId || null,
          tags: data.tags,
          answers: data.answers,
        });

        fetchQuestions(data.mission!!.id, data.answers);
      },
    }
  );

  const { mutate: editStudylogRequest } = useMutation(
    (data: StudylogForm) =>
      requestEditStudylog({
        id,
        accessToken: localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN) as string,
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

  const { user } = useContext(UserContext);
  const { accessToken, username } = user;

  useEffect(() => {
    if (mode === 'edit') {
      const author = fetchStudylogRequest.data?.data.author;

      if (author && username !== author.username) {
        alert(ALERT_MESSAGE.CANNOT_EDIT_OTHERS);
        history.push(`${PATH.STUDYLOG}/${id}`);
        return;
      }

      fetchStudylogRequest.refetch();
    }
  }, [mode, username, id]);

  useEffect(() => {
    if (mode !== 'create' || !tempSavedStudylog) {
      return;
    }

    const isTempSavedStudylogExist = Object.entries(tempSavedStudylog).some(
      ([_, value]) => value !== null
    );

    if (isTempSavedStudylogExist) {
      setStudylogContent({
        title: tempSavedStudylog.title ?? '',
        content: tempSavedStudylog.content,
        missionId: tempSavedStudylog.mission?.id ?? null,
        sessionId: tempSavedStudylog.session?.sessionId ?? null,
        tags: tempSavedStudylog.tags ?? [],
        answers: tempSavedStudylog.answers ?? [],
      });

      if (tempSavedStudylog.mission) {
        fetchQuestions(tempSavedStudylog.mission.id, tempSavedStudylog.answers);
      }
    }
  }, [mode, tempSavedStudylog]);

  const onSubmit = mode === 'edit' ? onCreateStudylog : onCreateStudylog;

  const content = studylogContent.content ? studylogContent.content : '';

  useEffect(() => {
    if (editorContentRef.current && content) {
      (editorContentRef.current as any).getInstance().setMarkdown(content);
    }
  }, [content]);

  return (
    <div css={MainContentStyle}>
      <form onSubmit={onSubmit}>
        <div css={[getFlexStyle({ columnGap: '1rem' })]}>
          <Container css={[getFlexStyle({ flexGrow: 1, flexDirection: 'column' })]}>
            <div>
              <Card css={[EditorStyle, markdownStyle, EditorWrapperStyle]}>
                <div css={[EditorTitleStyle]}>
                  <input
                    placeholder={'제목을 입력하세요'}
                    value={studylogContent.title}
                    readOnly={false}
                    onChange={onChangeTitle}
                  />
                </div>
                <SectionName>Content</SectionName>
                {/* FIXME: 임시방편 editor에 상태 값을 초기값으로 넣는 법 찾기 */}
                <Editor
                  height="50rem"
                  title={studylogContent.title}
                  editorContentRef={editorContentRef}
                  onChangeTitle={onChangeTitle}
                  content={content}
                />
              </Card>
              {questions && editorAnswersRef && (
                <Card
                  css={css`
                    padding: 2.5rem;
                    margin-top: 3rem;
                  `}
                >
                  <SectionName>Question</SectionName>
                  <QuestionAnswers
                    editable={true}
                    questions={questions}
                    editorAnswerRef={editorAnswersRef}
                  />
                </Card>
              )}
            </div>
          </Container>

          <div css={sidebarStyle}>
            <Sidebar
              mode={mode}
              questions={questions}
              setQuestions={setQuestions}
              studylogContent={studylogContent}
              setStudylogContent={setStudylogContent}
              editorAnswersRef={editorAnswersRef}
            />
          </div>
        </div>
        <div
          css={[
            getFlexStyle({ justifyContent: 'flex-end', columnGap: '2rem' }),
            css`
              margin-top: 3rem;
            `,
          ]}
        >
          {mode === 'create' && (
            <button type="button" css={[TempSaveButtonStyle]} onClick={onTempSaveStudylog}>
              임시 저장
            </button>
          )}
          <button type="submit" css={[SubmitButtonStyle]}>
            작성 완료
          </button>
        </div>
      </form>
    </div>
  );
};

export default StudylogEditor;
