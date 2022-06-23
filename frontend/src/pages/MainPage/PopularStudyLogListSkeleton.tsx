/** @jsxImportSource @emotion/react */

import { PopularStudylogListStyle } from './styles';
import Skeleton from '../../components/@shared/Skeleton/Skeleton';

const PopularStudyLogListSkeleton = () => {
  return (
    <div css={[PopularStudylogListStyle]}>
      <Skeleton width="24rem" height="32rem" borderRadius="1.6rem" />
      <Skeleton width="24rem" height="32rem" borderRadius="1.6rem" />
      <Skeleton width="24rem" height="32rem" borderRadius="1.6rem" />
      <Skeleton width="24rem" height="32rem" borderRadius="1.6rem" />
    </div>
  );
};

export default PopularStudyLogListSkeleton;
