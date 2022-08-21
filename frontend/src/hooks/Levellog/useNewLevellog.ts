import { useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { ALERT_MESSAGE, PATH, ERROR_MESSAGE } from '../../constants';
import { QnAType } from '../../models/Levellogs';
import { useCreateNewLevellogMutation } from '../queries/levellog';
import useBeforeunload from '../useBeforeunload';
import { Editor as ToastEditor } from '@toast-ui/react-editor';
import { SUCCESS_MESSAGE } from '../../constants/message';
import useSnackBar from '../useSnackBar';

export interface NewLevellogQnAListProps {
  QnAList: QnAType[];
  onAddQnA: () => void;
  onDeleteQnA: (index: number) => void;
  onChangeQuestion: (value: string, index: number) => void;
  onChangeAnswer: (value: string, index: number) => void;
}

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

  const [QnAList, setQnAList] = useState<QnAType[]>([{ question: '', answer: '' }]);

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

  const onAddQnA: () => void = () => {
    setQnAList((prev) => [...prev, { question: '', answer: '' }]);

    setTimeout(() => {
      window.scrollTo({ left: 0, top: document.body.scrollHeight, behavior: 'smooth' });
    }, 100);
  };

  const onDeleteQnA: (index: number) => void = (index) => {
    setQnAList((prev) => prev.filter((_, idx) => idx !== index));
  };

  const onChangeQuestion: (value: string, index: number) => void = (value, index) => {
    const changedQnAList = [...QnAList];
    changedQnAList[index].question = value;
    setQnAList(changedQnAList);
  };

  const onChangeAnswer: (value: string, index: number) => void = (value, index) => {
    const changedQnAList = [...QnAList];
    changedQnAList[index].answer = value;
    setQnAList(changedQnAList);
  };

  const NewLevellogQnAListProps: NewLevellogQnAListProps = {
    QnAList,
    onAddQnA,
    onChangeQuestion,
    onChangeAnswer,
    onDeleteQnA,
  };

  return {
    createNewLevellog,
    editorContentRef,
    onChangeTitle,
    title,
    NewLevellogQnAListProps,
    setQnAList,
  };
};

export default useNewLevellog;
