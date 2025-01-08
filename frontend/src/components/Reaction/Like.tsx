import { MouseEventHandler } from 'react';
import {Button, BUTTON_SIZE} from '..';

import { ReactComponent as LikeIcon } from '../../assets/images/heart-filled.svg';
import { ReactComponent as UnLikeIcon } from '../../assets/images/heart.svg';
import { LikeIconStyle } from './Like.styles';

interface Props {
  liked: boolean;
  likesCount?: number;
  onClick: MouseEventHandler<HTMLButtonElement>;
}

const Like = ({ liked, likesCount, onClick }: Props) => {
  const likeIconAlt = liked ? '좋아요' : '좋아요 취소';

  return (
    <Button type="button" alt={likeIconAlt} cssProps={LikeIconStyle} onClick={onClick}>
      {liked ? <LikeIcon width="2rem" height="2rem" /> : <UnLikeIcon width="2rem" height="2rem" />}
      {likesCount}
    </Button>
  );
};

export default Like;
