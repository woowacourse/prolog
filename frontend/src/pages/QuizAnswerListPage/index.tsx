/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useParams } from 'react-router';
import QuizAnswerList from '../../components/Lists/QuizAnswerList';
import MEDIA_QUERY from '../../constants/mediaQuery';
import { useGetQuiz, useGetQuizAnswerList } from '../../hooks/queries/essayanswer';
import { MainContentStyle } from '../../PageRouter';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle
} from '../../styles/flex.styles';
import { HeaderContainer, PostListContainer } from './styles';

const QuizAnswerListPage = () => {
  const { quizId } = useParams<{ quizId: string }>();

  const { data: essayAnswers } = useGetQuizAnswerList({ quizId });
  const { data: quiz } = useGetQuiz({ quizId });

  return (
    <div css={[MainContentStyle]}>
      <HeaderContainer>
        <div
          css={[
            FlexStyle,
            JustifyContentSpaceBtwStyle,
            AlignItemsCenterStyle,
            css`
              margin-bottom: 1rem;

              ${MEDIA_QUERY.xs} {
                flex-direction: column;
              }
            `,
          ]}
        >
          <h1
            css={css`
              font-size: 3.4rem;
            `}
          >
            {!!quiz && quiz.question} 🤔
          </h1>
        </div>
      </HeaderContainer>
      <PostListContainer>
        {(!essayAnswers || essayAnswers.length === 0) && '작성된 글이 없습니다.'}
        {!!essayAnswers && <QuizAnswerList essayAnswers={essayAnswers} />}
      </PostListContainer>
    </div>
  );
};

export default QuizAnswerListPage;
