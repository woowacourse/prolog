import {useGetQuizListByKeyword} from '../../hooks/queries/keywords';
import {KeywordResponse} from '../../models/Keywords';
import {SideSheet} from '../@shared/SideSheet/SideSheet';
import * as Styled from './KeywordDetailSideSheet.styles';
import {useContext} from "react";
import {UserContext} from "../../contexts/UserProvider";

interface KeywordDetailSideSheetProps {
  keywordDetail: KeywordResponse;
  handleCloseSideSheet: () => void;
}

const KeywordDetailSideSheet = ({
  keywordDetail,
  handleCloseSideSheet,
}: KeywordDetailSideSheetProps) => {
  const { name, keywordId, description } = keywordDetail;

  const { user: { isLoggedIn } } = useContext(UserContext);
  const { quizList } = useGetQuizListByKeyword({ keywordId });

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
                {isLoggedIn && (
                  <a href={`/quizzes/${quizId}/essay-answers/form`}>{index + 1}. {question}</a>
                )}
                {!isLoggedIn && (
                  <>{index + 1}. {question}</>
                )}
                &nbsp;/&nbsp;
                <a href={`/quizzes/${quizId}/essay-answers`}>답변 보러가기</a>
              </li>
            ))}
          </ol>
        </Styled.QuizSection>
      </Styled.Root>
    </SideSheet>
  );
};

export default KeywordDetailSideSheet;
