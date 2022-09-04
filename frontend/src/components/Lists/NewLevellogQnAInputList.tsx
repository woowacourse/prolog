import { ChangeEvent } from 'react';
import { QnAType } from '../../models/Levellogs';
import NewLevellogQnAInputItem from '../Items/NewLevellogQnAInputItem';

import * as S from './NewLevellogQnAInputList.styles';

export interface NewLevellogQnAListProps {
  QnAList: QnAType[];
  onAddQnA?: () => void;
  onDeleteQnA: (index: number) => void;
  onChangeQuestion: (value: string, index: number) => void;
  onChangeAnswer: (value: string, index: number) => void;
}

const NewLevellogQnAList = ({
  QnAList,
  onAddQnA,
  onDeleteQnA,
  onChangeQuestion,
  onChangeAnswer,
}: NewLevellogQnAListProps) => {
  return (
    <S.Container>
      <S.Label>Questions</S.Label>
      <S.QnAItemsWrapper>
        {QnAList.map(({ question, answer }, questionNumber) => (
          <NewLevellogQnAInputItem
            key={questionNumber}
            idx={questionNumber + 1}
            question={question}
            onChangeQuestion={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeQuestion(e.target.value, questionNumber);
            }}
            answer={answer}
            onChangeAnswer={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeAnswer(e.target.value, questionNumber);
            }}
            onDeleteQnA={() => {
              onDeleteQnA(questionNumber);
            }}
          />
        ))}
      </S.QnAItemsWrapper>
      {onAddQnA && <S.AddQnAButton onClick={onAddQnA} />}
    </S.Container>
  );
};

export default NewLevellogQnAList;
