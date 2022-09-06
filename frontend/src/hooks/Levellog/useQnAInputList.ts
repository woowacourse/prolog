import { useState } from 'react';
import { QnAType } from '../../models/Levellogs';

const useQnAInputList = () => {
  const [QnAList, setQnAList] = useState<QnAType[]>([{ question: '', answer: '' }]);

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

  return { QnAList, onAddQnA, onDeleteQnA, onChangeAnswer, onChangeQuestion, setQnAList };
};

export default useQnAInputList;
