import { useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { ALERT_MESSAGE, PATH, ERROR_MESSAGE } from '../../constants';
import { QnAType } from '../../models/Levellogs';
import { useCreateNewLevellogMutation } from '../queries/levellog';
import useBeforeunload from '../useBeforeunload';
import { SUCCESS_MESSAGE } from '../../constants/message';
import useSnackBar from '../useSnackBar';
import useQnAInputList from './useQnAInputList';

const useNewLevellog = () => {
  const history = useHistory();
  const editorContentRef = useRef<any>(null);
  const { openSnackBar } = useSnackBar();

  const [title, setTitle] = useState('');
  const onChangeTitle = (e) => {
    setTitle(e.target.value);
  };

  useBeforeunload(editorContentRef);

  const { mutate: createNewLevellogRequest } = useCreateNewLevellogMutation({
    onSuccess: () => {
      history.push(PATH.LEVELLOG);
      alert(SUCCESS_MESSAGE.CREATE_POST);
    },
    onError: (error: { code: number }) => {
      openSnackBar(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_POST_LEVELLOG);
    },
  });

  const { QnAList, onAddQnA, onChangeAnswer, onChangeQuestion, onDeleteQnA } = useQnAInputList();
  const NewLevellogQnAListProps = {
    QnAList,
    onAddQnA,
    onChangeQuestion,
    onChangeAnswer,
    onDeleteQnA,
  };

  const createNewLevellog = (e) => {
    e.preventDefault();

    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (title.length === 0) {
      alert(ALERT_MESSAGE.NO_TITLE);
      return;
    }

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    if (QnAList.length < 1) {
      alert(ALERT_MESSAGE.NO_QNA);
      return;
    }

    if (QnAList.some((QnA) => QnA.answer.length < 1 || QnA.question.length < 1)) {
      alert(ALERT_MESSAGE.NO_QUESTION_AND_ANSWER);
      return;
    }

    createNewLevellogRequest({
      title,
      content,
      levelLogs: [...QnAList],
    });
  };

  return {
    createNewLevellog,
    editorContentRef,
    onChangeTitle,
    title,
    NewLevellogQnAListProps,
  };
};

export default useNewLevellog;
