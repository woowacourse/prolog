import * as S from './LevellogQnaItem.styles';

const LevellogQnAItem = ({
  question,
  onChangeQuestion,
  answer,
  onChangeAnswer,
  idx,
  onDeleteQnA,
}) => {
  return (
    <S.Container>
      <S.DeleteQnAButton onClick={onDeleteQnA}>-</S.DeleteQnAButton>
      <S.QnAForm>
        <S.QnAQuestionWrapper>
          <S.QnAQuestionIndex>Q{idx + 1}</S.QnAQuestionIndex>
          <S.QnAQuestionInput
            type="text"
            value={question}
            onChange={onChangeQuestion}
            placeholder="질문을 입력해주세요"
          />
        </S.QnAQuestionWrapper>
        <S.QnAAnswerTextarea
          value={answer}
          onChange={onChangeAnswer}
          placeholder="답변을 입력해주세요"
          minRows={2}
        />
      </S.QnAForm>
    </S.Container>
  );
};

export default LevellogQnAItem;
