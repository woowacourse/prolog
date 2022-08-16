import { S } from './QnAList.styles';

const QnAList = ({ QnAList }) => {
  return (
    <S.Container>
      <S.SubTitle>Q &amp; A</S.SubTitle>
      <S.QnAsWrapper>
        {QnAList.map((QnA, idx) => (
          <S.QnA key={QnA.id}>
            <S.Question>{QnA.question}</S.Question>
            <S.Answer>{QnA.answer}</S.Answer>
          </S.QnA>
        ))}
      </S.QnAsWrapper>
    </S.Container>
  );
};

export default QnAList;
