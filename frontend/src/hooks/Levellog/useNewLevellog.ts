import { useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { ALERT_MESSAGE, PATH } from '../../constants';
import { QnAType } from '../../models/Levellogs';
import { useCreateNewLevellogMutation } from '../queries/levellog';
import useBeforeunload from '../useBeforeunload';
import { Editor as ToastEditor } from '@toast-ui/react-editor';
import { SUCCESS_MESSAGE } from '../../constants/message';

const useNewLevellog = () => {
  const history = useHistory();
  const editorContentRef = useRef<any>(null);
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

  const onAddQnA = () => {
    setQnAList((prev) => [...prev, { question: '', answer: '' }]);

    setTimeout(() => {
      window.scrollTo({ left: 0, top: document.body.scrollHeight, behavior: 'smooth' });
    }, 100);
  };

  const onDeleteQnA = (index) => {
    setQnAList((prev) => prev.filter((_, idx) => idx !== index));
  };

  const onChangeQuestion = (e, index) => {
    const changedQnAList = [...QnAList];
    changedQnAList[index].question = e.target.value;
    setQnAList(changedQnAList);
  };

  const onChangeAnswer = (e, index) => {
    const changedQnAList = [...QnAList];
    changedQnAList[index].answer = e.target.value;
    setQnAList(changedQnAList);
  };

  return {
    createNewLevellog,
    editorContentRef,
    onChangeTitle,
    title,
    QnAListProps: {
      QnAList,
      onAddQnA,
      onChangeQuestion,
      onChangeAnswer,
      onDeleteQnA,
    },
  };
};

export default useNewLevellog;
