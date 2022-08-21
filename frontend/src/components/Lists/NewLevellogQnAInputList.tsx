import { ChangeEvent } from 'react';
import { NewLevellogQnAListProps } from '../../hooks/Levellog/useNewLevellog';
import NewLevellogQnAInputItem from '../Items/NewLevellogQnAInputItem';

import * as S from './NewLevellogQnAInputList.styles';

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
        {QnAList.map(({ question, answer }, idx) => (
          <NewLevellogQnAInputItem
            key={idx}
            idx={idx}
            question={question}
            onChangeQuestion={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeQuestion(e.target.value, idx);
            }}
            answer={answer}
            onChangeAnswer={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeAnswer(e.target.value, idx);
            }}
            onDeleteQnA={() => {
              onDeleteQnA(idx);
            }}
          />
        ))}
      </S.QnAItemsWrapper>
      {onAddQnA && <S.AddQnAButton onClick={onAddQnA} />}
    </S.Container>
  );
};

export default NewLevellogQnAList;
