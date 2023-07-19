/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { MainContentStyle } from '../../PageRouter';
import EssayAnswerList from '../../components/Lists/QuizAnswerList';
import MEDIA_QUERY from '../../constants/mediaQuery';
import { useGetCurriculums } from '../../hooks/queries/curriculum';
import { useGetEssayAnswers } from '../../hooks/queries/essayanswer';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle
} from '../../styles/flex.styles';
import { HeaderContainer, PostListContainer } from './styles';
import { useLocation } from 'react-router';

const EssayAnswerListPage = () => {
  const { curriculums } = useGetCurriculums();
  const { search } = useLocation();
  const searchParams = new URLSearchParams(search);
  const curriculumId = Number(searchParams.get('curriculumId') ?? '1');
  const selectedCurriculum = (curriculums ?? []).find(curriculum => curriculum.id === curriculumId)?.name ?? '😎';

  const { data: essayAnswers } = useGetEssayAnswers({ curriculumId });

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
            {selectedCurriculum} 분야의 모든 로드맵 답변
          </h1>
        </div>
      </HeaderContainer>
      <PostListContainer>
        {(!essayAnswers || essayAnswers.length === 0) && '작성된 글이 없습니다.'}
        {!!essayAnswers && <EssayAnswerList essayAnswers={essayAnswers} showQuizTitle={true} />}
      </PostListContainer>
    </div>
  );
};

export default EssayAnswerListPage;
