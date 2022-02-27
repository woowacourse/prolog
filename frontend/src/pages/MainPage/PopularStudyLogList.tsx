/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { Studylog } from '../../models/Studylogs';
import { SectionHeaderGapStyle } from './styles';
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
      <ul
        css={css`
          width: 100%;
          height: 34rem;

          display: grid;
          justify-content: content;
          align-items: center;
          grid-template-columns: repeat(10, 1fr);

          position: relative;

          > *:not(:last-child) {
            margin-right: 1.6rem;
          }

          > *:last-child {
            margin-right: 0.2rem;
          }

          overflow-x: scroll;
        `}
      >
        {studylogs?.map((item: Studylog) => (
          <li key={item.id}>
            <PopularStudylogItem item={item} />
          </li>
        ))}
      </ul>
      <div
        css={css`
          width: 3.6rem;
          height: 300px;

          background-color: rgba(0, 0, 0, 0);

          position: absolute;
          bottom: 0;
          right: 0;

          display: flex;
          align-items: center;

          button {
            width: 3.2rem;
            height: 3.2rem;

            margin-right: auto;

            background-color: rgba(0, 0, 0, 0.7);
            border-radius: 50%;

            color: white;

            opacity: 0;
          }

          :hover {
            button {
              opacity: 1;
            }
          }
        `}
      >
        <button>다음</button>
      </div>
    </section>
  );
};

export default PopularStudyLogList;
