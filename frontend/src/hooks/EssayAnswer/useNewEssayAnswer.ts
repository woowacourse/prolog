import { useRef } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import useBeforeunload from '../useBeforeunload';
import useSnackBar from '../useSnackBar';
import { useCreateNewEssayAnswerMutation } from '../queries/essayanswer';

import { SUCCESS_MESSAGE } from '../../constants/message';
import { ALERT_MESSAGE, ERROR_MESSAGE } from '../../constants';

const useNewEssayAnswer = () => {
  const history = useHistory();
  const editorContentRef = useRef<any>(null);
  const {openSnackBar} = useSnackBar();

  const {quizId} = useParams<{ quizId: string }>();
  const quizIdNumber = Number(quizId);

  useBeforeunload(editorContentRef);

  const {mutate: createNewEssayAnswerRequest} = useCreateNewEssayAnswerMutation({
    onSuccess: () => {
      history.push(`/quizzes/${quizId}/essay-answers`);
      alert(SUCCESS_MESSAGE.CREATE_POST);
    },
    onError: (error: { code: number }) => {
      openSnackBar(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_POST_ESSAY_ANSWER);
    },
  });

  const createNewEssayAnswer = (e) => {
    e.preventDefault();

    const content: string = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    createNewEssayAnswerRequest({
      quizId: quizIdNumber,
      answer: content,
    });
  };

  return {
    createNewEssayAnswer,
    quizId: quizIdNumber,
    editorContentRef,
  };
};

export default useNewEssayAnswer;
