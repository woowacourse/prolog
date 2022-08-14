import { useRef, useState } from 'react';
import { QnAType } from '../../models/Levellogs';
import { useCreateNewLevellog } from '../queries/levellog';
import useBeforeunload from '../useBeforeunload';

const useNewLevellog = () => {
  const editorContentRef = useRef<any>(null);
  const [title, setTitle] = useState('');
  const onChangeTitle = (e) => {
    setTitle(e.target.value);
  };

  useBeforeunload(editorContentRef);

  const { mutate: createNewLevellogRequest } = useCreateNewLevellog();

  const [QnAList, setQnAList] = useState<QnAType[]>([{ question: '', answer: '' }]);

  const createNewLevellog = () => {
    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

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
