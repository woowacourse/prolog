/** @jsxImportSource @emotion/react */

import { ReactComponent as CommentIcon } from '../../assets/images/comment.svg';
import { CommentContainerStyle } from './CommentCount.styles';

interface CommentCountProps {
  count: number;
}

const CommentCount = ({ count }: CommentCountProps) => {
  return (
    <div css={[CommentContainerStyle]}>
      <CommentIcon width={'20px'} height={'20px'} />
      {count}
    </div>
  );
};

export default CommentCount;
