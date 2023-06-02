/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { MainContentStyle } from '../../PageRouter';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { HeaderContainer, PostListContainer } from './styles';
import { useEssayAnswerList } from '../../hooks/EssayAnswer/useEssayAnswerList';
import EssayAnswerList from '../../components/Lists/EssayAnswerList';
import mediaQuery from '../../utils/mediaQuery';

const EssayAnswerListPage = () => {
  const { quiz, essayAnswers } = useEssayAnswerList();

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

              ${mediaQuery.xs} {
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
        {!!essayAnswers && <EssayAnswerList essayAnswers={essayAnswers} />}
      </PostListContainer>
    </div>
  );
};

export default EssayAnswerListPage;
