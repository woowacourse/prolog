/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import Skeleton from '../../components/@shared/Skeleton/Skeleton';

const RecentStudylogListSkeleton = () => {
  return (
    <ul
      css={css`
        > li:not(:last-child) {
          margin-bottom: 1.6rem;
        }
      `}
    >
      <li>
        <Skeleton width="100%" height="20rem" borderRadius="1.6rem" />
      </li>
      <li>
        <Skeleton width="100%" height="20rem" borderRadius="1.6rem" />
      </li>
      <li>
        <Skeleton width="100%" height="20rem" borderRadius="1.6rem" />
      </li>
    </ul>
  );
};

export default RecentStudylogListSkeleton;
