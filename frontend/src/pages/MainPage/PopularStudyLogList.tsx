/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { studyLogCategory, StudyLogResponse } from '../../models/Studylogs';
import { PopularStudylogListButton, PopularStudylogListButtonIcon, PopularStudylogListStyle, SectionHeaderGapStyle, StyledChip } from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import { useState } from 'react';
import { AlignItemsCenterStyle, FlexStyle } from '../../styles/flex.styles';
import type { ValueOf } from '../../types/utils';
import { getKeyByValue } from '../../utils/object';
import { ReactComponent as CaretLeftIcon } from '../../assets/images/caret-left.svg';
import { ReactComponent as CaretRightIcon } from '../../assets/images/caret-right.svg';
import useMediaQuery from '../../hooks/useMediaQuery';
import { ResetScrollBar } from '../../styles/reset.styles';

type Category = ValueOf<typeof studyLogCategory>;

const PopularStudyLogList = ({ studylogs }: { studylogs: StudyLogResponse }): JSX.Element => {
  const [selectedCategory, setSelectedCategory] = useState<Category>(studyLogCategory.allResponse);
  const popularStudyLogs = studylogs[getKeyByValue(studyLogCategory, selectedCategory)].data;

  const isLgMobile = useMediaQuery('screen and (max-width: 960px)');
  const isXsMobile = useMediaQuery('screen and (max-width: 380px)');

  const itemsPerPage = isXsMobile ? 1 : isLgMobile ? 2 : 3;
  const minPage = 1;
  const maxPage = Math.ceil(popularStudyLogs.length / itemsPerPage);
  const [page, setPage] = useState(1);

  const paginatedPopularStudyLogs = popularStudyLogs.slice(itemsPerPage * (page - 1), itemsPerPage * page);

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
        <ul
          css={[
            FlexStyle,
            ResetScrollBar,
            css`
              gap: 1.4rem;
              @media (max-width: 360px) {
                width: 100%;
                overflow-x: scroll;
              }
            `,
          ]}
        >
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
          <CaretLeftIcon css={PopularStudylogListButtonIcon} />
        </button>
        <ul css={PopularStudylogListStyle}>
          {paginatedPopularStudyLogs.map((studylog) => (
            <li key={studylog.id}>
              <PopularStudylogItem item={studylog} />
            </li>
          ))}
        </ul>
        <button onClick={increasePage} css={[PopularStudylogListButton, css`
          opacity: ${page === maxPage ? 0.15 : 'initial'};
        `]}>
          <CaretRightIcon css={PopularStudylogListButtonIcon} />
        </button>
      </div>
    </section>
  );
};

export default PopularStudyLogList;
