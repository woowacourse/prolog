/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';
import { css } from '@emotion/react';

import StudyLogList from '../../components/Lists/StudylogList';
import { PATH } from '../../constants';
import {
  FlexStyle,
  AlignItemsCenterStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';

const RecentStudylogList = ({ studylogs }: { studylogs: Prolog.StudyLog[] }) => {
  return (
    <section>
      <div
        css={[
          FlexStyle,
          JustifyContentSpaceBtwStyle,
          AlignItemsCenterStyle,
          css`
            a:hover {
              text-decoration: underline;
            }
          `,
        ]}
      >
        <h2
          css={css`
            margin-bottom: 1.2rem;
            padding-left: 1.2rem;
          `}
        >
          📚 최신 학습로그
        </h2>
        <Link to={PATH.STUDYLOG}>{`더보기 >`}</Link>
      </div>
      {studylogs?.length === 0 && '작성된 글이 없습니다.'}
      {!!studylogs?.length && <StudyLogList studylogs={studylogs} />}
    </section>
  );
};

export default RecentStudylogList;
