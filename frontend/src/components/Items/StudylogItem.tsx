/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import { Card, ProfileChip } from '..';
import ViewCount from '../Count/ViewCount';
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
import {
    AlignItemsCenterStyle,
    AlignItemsEndStyle,
    FlexColumnStyle, FlexRowStyle,
    FlexStyle,
    getFlexStyle,
    JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import CommentCount from '../Count/CommentCount';
import Like from '../Reaction/Like';
import LikeCount from '../Count/LikeCount';
import * as Styled from '../Comment/Comment.style';
import ProfileChipMax from "../ProfileChipMax/ProfileChipMax";

interface Props {
  studylog: Studylog;
  onClick: () => void;
  onProfileClick: (event?: MouseEvent) => void;
}

const StudylogItem = ({ studylog, onClick, onProfileClick }: Props) => {
  const {
    author,
    title,
    tags,
    read: isRead,
    viewCount,
    mission,
    createdAt,
    commentCount,
    likesCount,
  } = studylog;

  return (
    <Card size="EXTRA_SMALL" cssProps={CardStyle} onClick={onClick}>
      <div css={ContentStyle}>
        <div css={[FlexStyle, JustifyContentSpaceBtwStyle, DescriptionStyle]}>
          <div>
            <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
              <p css={MissionStyle}>{mission?.name}</p>
              <div
                css={css`
                  color: ${COLOR.LIGHT_GRAY_900};
                  font-size: 1.4rem;
                  margin-left: 2rem;
                `}
              >
                {new Date(createdAt).toLocaleDateString('ko-KR', {
                  year: 'numeric',
                  month: 'long',
                  day: 'numeric',
                })}
              </div>
            </div>

            <h3>{title}</h3>
          </div>
          <ul css={TagListStyle}>
            {tags?.map(({ id, name }) => (
              <span key={id}>{`#${name} `}</span>
            ))}
          </ul>
          <div css={[FlexStyle, AlignItemsEndStyle]}>
            <LikeCount count={likesCount}></LikeCount>
            <CommentCount count={commentCount} />
            <ViewCount count={viewCount} />
          </div>
        </div>
        <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
          <ProfileChipMax
            imageSrc={author.imageUrl}
            cssProps={ProfileChipLocationStyle}
            onClick={onProfileClick}
          >
            {author.nickname}
          </ProfileChipMax>
        </div>
      </div>
    </Card>
  );
};

export default StudylogItem;
