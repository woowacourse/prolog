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

const StudylogItem = ({
  studylog,
  onClick,
  onProfileClick,
}: {
  studylog: Studylog;
  onClick: () => void;
  onProfileClick: (event?: MouseEvent) => void;
}) => {
  const { author, mission, title, tags, read: isRead, viewCount } = studylog;

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
          <p css={MissionStyle}>{mission.name}</p>
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
          <ViewCount count={viewCount} />
        </div>
      </div>
    </Card>
  );
};

export default StudylogItem;
