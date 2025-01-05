/** @jsxImportSource @emotion/react */

import {css} from '@emotion/react';

import {MainContentStyle} from '../../PageRouter';

import Editor from '../../components/Editor/Editor';
import {FlexColumnStyle, FlexStyle} from '../../styles/flex.styles';
import styled from '@emotion/styled';
import {ALERT_MESSAGE, COLOR} from '../../constants';
import {useContext, useEffect, useRef, useState} from "react";
import { UserContext } from '../../contexts/UserProvider';
import { useHistory, useParams } from 'react-router';
import { useEditEssayAnswer, useGetEssayAnswer } from '../../hooks/queries/essayanswer';
import { EssayAnswer } from '../../models/EssayAnswers';

const EditEssayAnswerPage = () => {
  const history = useHistory();
  const { id } = useParams<{ id: string }>();

  const { user: { username } } = useContext(UserContext);

  const [quizTitle, setQuizTitle] = useState<string>('');
  const [answer, setAnswer] = useState<string>('');

  const editorContentRef = useRef<any>(null);

  const previousEssayAnswer = useGetEssayAnswer(
    { essayAnswerId: id },
    {
      onSuccess: ({ quiz: { question }, answer }: EssayAnswer) => {
        setAnswer(answer);
        setQuizTitle(question);
      },
    },
  );

  const author = previousEssayAnswer.data?.author;

  const { mutate: editEssayRequest } = useEditEssayAnswer({
    essayAnswerId: Number(id),
  });

  const onEditEssayAnswer = () => {
    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    editEssayRequest({
      answer: content,
    });
  };

  useEffect(() => {
    if (author && username !== author.username) {
      alert(ALERT_MESSAGE.CANNOT_EDIT_OTHERS);
      history.goBack();
    }
  }, [username, author]);

  useEffect(() => {
    previousEssayAnswer.refetch();
  }, [id]);

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
        height="40vh"
        content={answer}
        editorContentRef={editorContentRef}
      />
      <SubmitButton type="submit" onClick={onEditEssayAnswer}>
        제출하기
      </SubmitButton>
    </div>
  );
};

export default EditEssayAnswerPage;

const SubmitButton = styled.button`
  background-color: ${COLOR.LIGHT_BLUE_400};
  width: 100%;
  border-radius: 4px;
  padding: 1rem;
`;
