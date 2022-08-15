import LevellogQnAItem from '../Items/LevellogQnAItem';

import * as S from './LevellogQnAList.styles';

const LevellogQnAList = ({ QnAListProps }) => {
  const { QnAList, onAddQnA, onDeleteQnA, onChangeQuestion, onChangeAnswer } = QnAListProps;

  return (
    <S.Container>
      <S.Label>Questions</S.Label>
      <S.QnAItemsWrapper>
        {QnAList.map(({ question, answer }, idx) => (
          <LevellogQnAItem
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

export default LevellogQnAList;
