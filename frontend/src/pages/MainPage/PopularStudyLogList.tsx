/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { studyLogCategory, StudyLogResponse } from '../../models/Studylogs';
import { PopularStudylogListStyle, SectionHeaderGapStyle, StyledChip } from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import { useState } from 'react';
import { AlignItemsCenterStyle, FlexStyle } from '../../styles/flex.styles';
import type { ValueOf } from '../../types/utils';
import { getKeyByValue } from '../../utils/object';

type Category = ValueOf<typeof studyLogCategory>;

const PopularStudyLogList = ({ studylogs }: { studylogs: StudyLogResponse }): JSX.Element => {
  const [selectedCategory, setSelectedCategory] = useState<Category>(studyLogCategory.allResponse);

  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <div
        css={[
          SectionHeaderGapStyle,
          FlexStyle,
          AlignItemsCenterStyle,
          css`
            @media (max-width: 760px) {
              flex-direction: column;
            }
          `,
        ]}
      >
        <h2>😎 인기있는 학습로그</h2>
        <ul css={[FlexStyle]}>
          {Object.values(studyLogCategory).map((item) => (
            <li key={item}>
              <StyledChip
                active={selectedCategory === item}
                onClick={() => setSelectedCategory(item)}
              >
                {item}
              </StyledChip>
            </li>
          ))}
        </ul>
      </div>
      <ul css={[PopularStudylogListStyle]}>
        {studylogs[
          getKeyByValue(studyLogCategory, selectedCategory) as keyof typeof studylogs
        ].data.map((studylog) => (
          <li key={studylog.id}>
            <PopularStudylogItem item={studylog} />
          </li>
        ))}
      </ul>
    </section>
  );
};

export default PopularStudyLogList;
