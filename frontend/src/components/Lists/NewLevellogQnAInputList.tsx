import NewLevellogQnAInputItem from '../Items/NewLevellogQnAInputItem';

import * as S from './NewLevellogQnAInputList.styles';

const NewLevellogQnAList = ({ QnAListProps }) => {
  const { QnAList, onAddQnA, onDeleteQnA, onChangeQuestion, onChangeAnswer } = QnAListProps;

  return (
    <S.Container>
      <S.Label>Questions</S.Label>
      <S.QnAItemsWrapper>
        {QnAList.map(({ question, answer }, idx) => (
          <NewLevellogQnAInputItem
            key={idx}
            idx={idx}
            question={question}
            onChangeQuestion={(e) => {
              onChangeQuestion(e, idx);
            }}
            answer={answer}
            onChangeAnswer={(e) => {
              onChangeAnswer(e, idx);
            }}
            onDeleteQnA={() => {
              onDeleteQnA(idx);
            }}
          />
        ))}
      </S.QnAItemsWrapper>
      <S.AddQnAButton onClick={onAddQnA} />
    </S.Container>
  );
};

export default NewLevellogQnAList;
