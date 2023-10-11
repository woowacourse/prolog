/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useState } from 'react';
import { useLocation } from 'react-router';
import { MainContentStyle } from '../../PageRouter';
import EssayAnswerList from '../../components/Lists/QuizAnswerList';
import RoadmapFilter from '../../components/RoadmapFilter/RoadmapFilter';
import MEDIA_QUERY from '../../constants/mediaQuery';
import { useGetCurriculums } from '../../hooks/queries/curriculum';
import { useGetEssayAnswers } from '../../hooks/queries/essayanswer';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { HeaderContainer, PostListContainer } from './styles';

export interface FilterlingType {
  curriculumId: number;
  keywordId: number;
  quizIds: number[];
  memberIds: number[];
}

const EssayAnswerListPage = () => {
  const { curriculums } = useGetCurriculums();
  const { search } = useLocation();
  const [searchKeyword, setSearchKeyword] = useState<string>('');
  const searchParams = new URLSearchParams(search); // keywordId quizIds memberIds
  const curriculumId = Number(searchParams.get('curriculumId') ?? '1');
  const keywordId = Number(searchParams.get('keywordId') ?? undefined);
  const quizIds = searchParams.get('quizIds')?.split(',').map(Number) ?? undefined;
  const memberIds = searchParams.get('memberIds')?.split(',').map(Number) ?? undefined;
  const selectedCurriculum =
    (curriculums ?? []).find((curriculum) => curriculum.id === curriculumId)?.name ?? '😎';

  const { data: essayAnswers } = useGetEssayAnswers({
    curriculumId,
    keywordId,
    quizIds,
    memberIds,
  });

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
      <RoadmapFilter searchKeyword={searchKeyword} setSearchKeyword={setSearchKeyword} />

      <PostListContainer>
        {(!essayAnswers || essayAnswers.length === 0) && '작성된 글이 없습니다.'}
        {!!essayAnswers && <EssayAnswerList essayAnswers={essayAnswers} showQuizTitle={true} />}
      </PostListContainer>
    </div>
  );
};

export default EssayAnswerListPage;
