/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { studyLogCategory, StudyLogResponse } from '../../models/Studylogs';
import { PopularStudylogListButton, PopularStudylogListStyle, SectionHeaderGapStyle, StyledChip } from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import { useState } from 'react';
import { AlignItemsCenterStyle, FlexStyle } from '../../styles/flex.styles';
import type { ValueOf } from '../../types/utils';
import { getKeyByValue } from '../../utils/object';
import { ReactComponent as CaretLeftIcon } from '../../assets/images/caret-left.svg';
import { ReactComponent as CaretRightIcon } from '../../assets/images/caret-right.svg';

type Category = ValueOf<typeof studyLogCategory>;

const ITEMS_PER_PAGE = 3;

const PopularStudyLogList = ({ studylogs }: { studylogs: StudyLogResponse }): JSX.Element => {
  const [selectedCategory, setSelectedCategory] = useState<Category>(studyLogCategory.allResponse);
  const popularStudyLogs = studylogs[getKeyByValue(studyLogCategory, selectedCategory)].data;

  const minPage = 1;
  const maxPage = Math.ceil(popularStudyLogs.length / ITEMS_PER_PAGE);
  const [page, setPage] = useState(1);

  const paginatedPopularStudyLogs = popularStudyLogs.slice(ITEMS_PER_PAGE * (page - 1), ITEMS_PER_PAGE * page);

  const increasePage = () => {
    setPage((page) => Math.min(maxPage, page + 1));
  };

  const decreasePage = () => {
    setPage((page) => Math.max(minPage, page - 1));
  };

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
      <div css={css`
        display: flex;

        & > ul {
          flex: 1;
        }
      `}>
        <button onClick={decreasePage} css={[PopularStudylogListButton, css`
          opacity: ${page === minPage ? 0.15 : 'initial'};
        `]}>
          <CaretLeftIcon width={20} fill="rgba(0, 0, 0, 0.7)" />
        </button>
        <ul css={[PopularStudylogListStyle, css`
          overflow: hidden;
        `]}>
          {paginatedPopularStudyLogs.map((studylog) => (
            <li key={studylog.id} css={css`
              flex: 1;
            `}>
              <PopularStudylogItem item={studylog} />
            </li>
          ))}
        </ul>
        <button onClick={increasePage} css={[PopularStudylogListButton, css`
          opacity: ${page === maxPage ? 0.15 : 'initial'};
        `]}>
          <CaretRightIcon width={20} fill="rgba(0, 0, 0, 0.7)" />
        </button>
      </div>
    </section>
  );
};

export default PopularStudyLogList;
