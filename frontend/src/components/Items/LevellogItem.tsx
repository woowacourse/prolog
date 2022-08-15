/** @jsxImportSource @emotion/react */

import {
  CardStyle,
  ContentStyle,
  DescriptionStyle,
  ProfileChipLocationStyle,
} from './StudylogItem.styles';
import { AlignItemsEndStyle, FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import Card from '../Card/Card';
import ProfileChip from '../ProfileChip/ProfileChip';
import { NoDefaultHoverLink } from './LevellogItem.styles';

const LevellogItem = ({ levellog }) => {
  const { author, title } = levellog;

  return (
    <Card size="SMALL" cssProps={CardStyle}>
      <div css={ContentStyle}>
        <div css={DescriptionStyle}>
          <h3>{title}</h3>
        </div>
        <div css={[FlexStyle, FlexColumnStyle, AlignItemsEndStyle]}>
          <NoDefaultHoverLink to={`/${author.username}`}>
            <ProfileChip imageSrc={author.imageUrl} cssProps={ProfileChipLocationStyle}>
              {author.nickname}
            </ProfileChip>
          </NoDefaultHoverLink>
        </div>
      </div>
    </Card>
  );
};

export default LevellogItem;
