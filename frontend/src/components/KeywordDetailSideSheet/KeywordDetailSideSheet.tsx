import { useGetQuizListByKeyword } from '../../hooks/queries/keywords';
import { KeywordResponse } from '../../models/Keywords';
import { SideSheet } from '../@shared/SideSheet/SideSheet';
import * as Styled from './KeywordDetailSideSheet.styles';

interface KeywordDetailSideSheetProps {
  keywordDetail: KeywordResponse;
  sessionId: number;
  handleCloseSideSheet: () => void;
}

const KeywordDetailSideSheet = ({
  keywordDetail,
  sessionId,
  handleCloseSideSheet,
}: KeywordDetailSideSheetProps) => {
  const { name, keywordId, order, importance, description } = keywordDetail;

  const { quizList } = useGetQuizListByKeyword({ sessionId, keywordId });

  return (
    <SideSheet onClickBackdrop={handleCloseSideSheet}>
      <Styled.Root>
        <Styled.DescriptionSection>
          <h2>{name}</h2>
          <p>{description}</p>
        </Styled.DescriptionSection>

        <Styled.QuizSection>
          <h3>Quiz</h3>
          <ol>
            {quizList?.map(({ quizId, question }, index) => (
              <li key={quizId}>
                <a href={`/quizzes/${quizId}/essay-answers`}>{index + 1}. {question}</a>
              </li>
            ))}
          </ol>
        </Styled.QuizSection>
      </Styled.Root>
    </SideSheet>
  );
};

export default KeywordDetailSideSheet;
