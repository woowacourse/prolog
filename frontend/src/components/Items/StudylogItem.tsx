/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import { Card, ProfileChip } from '..';
import ViewCount from '../ViewCount/ViewCount';
import { Studylog } from '../../models/Studylogs';
import { COLOR } from '../../enumerations/color';

import {
  CardStyle,
  ContentStyle,
  DescriptionStyle,
  MissionStyle,
  TagListStyle,
  ProfileChipLocationStyle,
} from './StudylogItem.styles';
import { AlignItemsEndStyle, FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import CommentCount from '../CommentCount/CommentCount';

interface Props {
  studylog: Studylog;
  onClick: () => void;
  onProfileClick: (event?: MouseEvent) => void;
}

const StudylogItem = ({ studylog, onClick, onProfileClick }: Props) => {
  const { author, title, tags, read: isRead, viewCount, session, commentCount } = studylog;

  return (
    <Card
      size="SMALL"
      cssProps={
        isRead
          ? css`
              ${CardStyle};
              background-color: ${COLOR.LIGHT_GRAY_100};
            `
          : CardStyle
      }
      onClick={onClick}
    >
      <div css={ContentStyle}>
        <div css={DescriptionStyle}>
          <p css={MissionStyle}>{session?.name}</p>
          <h3>{title}</h3>
          <ul css={TagListStyle}>
            {tags?.map(({ id, name }) => (
              <span key={id}>{`#${name} `}</span>
            ))}
          </ul>
        </div>
        <div css={[FlexStyle, FlexColumnStyle, AlignItemsEndStyle]}>
          <ProfileChip
            imageSrc={author.imageUrl}
            cssProps={ProfileChipLocationStyle}
            onClick={onProfileClick}
          >
            {author.nickname}
          </ProfileChip>
          <CommentCount count={commentCount} />
          <ViewCount count={viewCount} />
        </div>
      </div>
    </Card>
  );
};

export default StudylogItem;
