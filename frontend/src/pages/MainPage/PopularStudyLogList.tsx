/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { Studylog } from '../../models/Studylogs';
import { PopularStudylogListStyle, SectionHeaderGapStyle } from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import PopularStudyLogListSkeleton from './PopularStudyLogListSkeleton';

const PopularStudyLogList = ({ studylogs }: { studylogs: Studylog[] }): JSX.Element => {
  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <h2 css={[SectionHeaderGapStyle]}>😎 인기있는 학습로그</h2>
      {studylogs.length !== 0 ? (
        <ul css={[PopularStudylogListStyle]}>
          {studylogs?.map((item: Studylog) => (
            <li key={item.id}>
              <PopularStudylogItem item={item} />
            </li>
          ))}
        </ul>
      ) : (
        <PopularStudyLogListSkeleton />
      )}
    </section>
  );
};

export default PopularStudyLogList;
