import { Button, BUTTON_SIZE } from '..';

import likedIcon from '../../assets/images/heart-filled.svg';
import unLikeIcon from '../../assets/images/heart.svg';
import { LikeIconStyle } from './Like.styles';

interface Props {
  liked: boolean;
  likesCount: number;
  onClick: () => void;
}

const Like = ({ liked, likesCount, onClick }: Props) => {
  const likeIcon = liked ? likedIcon : unLikeIcon;
  const likeIconAlt = liked ? '좋아요' : '좋아요 취소';

  return (
    <Button
      type="button"
      size={BUTTON_SIZE.X_SMALL}
      icon={likeIcon}
      alt={likeIconAlt}
      cssProps={LikeIconStyle}
      onClick={onClick}
    >
      {likesCount ?? 0}
    </Button>
  );
};

export default Like;
