/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useEffect, useMemo, useState } from 'react';
import { useHistory, useLocation } from 'react-router';
import { MainContentStyle } from '../../PageRouter';
import EssayAnswerList from '../../components/Lists/QuizAnswerList';
import RoadmapFilter from './components/RoadmapFilter/RoadmapFilter';
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
  curriculumId: string;
  keywordId: string;
  quizIds: string;
  memberIds: string;
}

const EssayAnswerListPage = () => {
  const history = useHistory();
  const { curriculums } = useGetCurriculums();
  const { search } = useLocation();

  const [filter, setFilter] = useState<Record<string, string>>(() =>
    Object.fromEntries(new URLSearchParams(search).entries())
  );

  const selectedCurriculum =
    (curriculums ?? []).find((curriculum) => curriculum.id === Number(filter.curriculumId))?.name ??
    '😎';

  const { curriculumId, keywordId, quizIds, memberIds } = filter;
  const { data: { data: essayAnswers } = { data: [] } } = useGetEssayAnswers({
    curriculumId: Number(curriculumId),
    keywordId: keywordId ? Number(keywordId) : undefined,
    quizIds: quizIds ? quizIds.split(',').map(Number) : undefined,
    memberIds: memberIds ? memberIds.split(',').map(Number) : undefined,
  });

  const handleFilterChange = (filter: Record<string, string>) => {
    filter['curriculumId'] = curriculumId;
    setFilter(filter);
  }

  useEffect(() => {
    history.replace(`/essay-answers?${new URLSearchParams(filter).toString()}`);
  }, [filter]);

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

      <RoadmapFilter curriculumId={Number(curriculumId)} filter={filter} onFilterChange={handleFilterChange} />

      <PostListContainer>
        {(!essayAnswers || essayAnswers.length === 0) && '작성된 글이 없습니다.'}
        {!!essayAnswers && <EssayAnswerList essayAnswers={essayAnswers} showQuizTitle={true} />}
      </PostListContainer>
    </div>
  );
};

export default EssayAnswerListPage;
