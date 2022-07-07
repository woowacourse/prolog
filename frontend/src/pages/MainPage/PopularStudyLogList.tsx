/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { Studylog } from '../../models/Studylogs';
import { PopularStudylogListStyle, SectionHeaderGapStyle } from './styles';
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
<<<<<<< HEAD
        {studylogs?.map((item: Studylog) => (
          <li key={item.id}>
            <PopularStudylogItem item={item} />
          </li>
        ))}
=======
        {studylogs[getKeyByValue(studyLogCategory, selectedCategory) as Category].data.map(
          ({ studylogResponse, scrapedCount }) => (
            <li key={studylogResponse.id}>
              <PopularStudylogItem item={{ ...studylogResponse, scrapedCount }} />
            </li>
          )
        )}
>>>>>>> 950ce71 (fix: 인기있는 학습로그 api 명세 변경에 따른 수정)
      </ul>
    </section>
  );
};

export default PopularStudyLogList;
