/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { MainContentStyle } from '../../PageRouter';

import { useState } from 'react';
import { useQuery, UseQueryResult } from 'react-query';
import { AxiosError, AxiosResponse } from 'axios';

import Editor from '../../components/Editor/Editor';
import { FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import { COLOR } from '../../constants';
import useNewEssayAnswer from '../../hooks/EssayAnswer/useNewEssayAnswer';
import { Quiz } from '../../models/Keywords';
import REACT_QUERY_KEY from '../../constants/reactQueryKey';
import { requestGetQuiz } from '../../apis/essayanswers';

const NewEssayAnswerPage = () => {
  const {
    createNewEssayAnswer,
    quizId,
    editorContentRef,
  } = useNewEssayAnswer();

  const [quizTitle, setQuizTitle] = useState<string>('퀴즈 제목');

  const fetchQuiz: UseQueryResult<AxiosResponse<Quiz>, AxiosError> = useQuery(
    [REACT_QUERY_KEY.QUIZ, quizId],
    () => requestGetQuiz(quizId),
    {
      onSuccess: ({data}) => {
        setQuizTitle(data.question)
      }
    }
  );

  return (
    <div
      css={[
        MainContentStyle,
        FlexStyle,
        FlexColumnStyle,
        css`
          gap: 30px;
        `,
      ]}
    >
      <Editor
        title={quizTitle}
        titleReadOnly={true}
        height='40vh'
        editorContentRef={editorContentRef}
      />
      <SubmitButton type='submit' onClick={createNewEssayAnswer}>
        제출하기
      </SubmitButton>
    </div>
  );
};

export default NewEssayAnswerPage;

const SubmitButton = styled.button`
  background-color: ${COLOR.LIGHT_BLUE_400};
  width: 100%;
  border-radius: 4px;
  padding: 1rem;
`;
