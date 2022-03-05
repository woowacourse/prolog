/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { Studylog } from '../../models/Studylogs';
import {
  PopularStudylogListRightControlStyle,
  PopularStudylogListStyle,
  SectionHeaderGapStyle,
} from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';

const PopularStudyLogList = ({ studylogs }: { studylogs: Studylog[] }): JSX.Element => {
  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <h2 css={[SectionHeaderGapStyle]}>😎 인기있는 학습로그</h2>
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
