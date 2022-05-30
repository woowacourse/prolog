/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { Studylog } from '../../models/Studylogs';
import { PopularStudylogListStyle, SectionHeaderGapStyle, StyledChip } from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import { useState } from 'react';
import { AlignItemsCenterStyle, FlexStyle } from '../../styles/flex.styles';

type Category = '전체' | '프론트엔드' | '백엔드';

const PopularStudyLogList = ({ studylogs }: { studylogs: Studylog[] }): JSX.Element => {
  const [selectedCategory, setSelectedCategory] = useState<Category>('전체');

  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <div css={[SectionHeaderGapStyle, FlexStyle, AlignItemsCenterStyle]}>
        <h2>😎 인기있는 학습로그</h2>
        <ul css={[FlexStyle]}>
          <li>
            {/* onClick 하면 selectedCategory 변경, 서버 데이터 패칭 */}
            <StyledChip active={selectedCategory === '전체'}>전체</StyledChip>
          </li>
          <li>
            <StyledChip active={selectedCategory === '프론트엔드'}>프론트엔드</StyledChip>
          </li>
          <li>
            <StyledChip active={selectedCategory === '백엔드'}>백엔드</StyledChip>
          </li>
        </ul>
      </div>
      <ul css={[PopularStudylogListStyle]}>
        {studylogs?.map((item: Studylog) => (
          <li key={item.id}>
            <PopularStudylogItem item={item} />
          </li>
        ))}
      </ul>
    </section>
  );
};

export default PopularStudyLogList;
