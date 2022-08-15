/** @jsxImportSource @emotion/react */

import { ContentStyle, DescriptionStyle, ProfileChipLocationStyle } from './StudylogItem.styles';
import { AlignItemsEndStyle, FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import Card from '../Card/Card';
import ProfileChip from '../ProfileChip/ProfileChip';
import { Link } from 'react-router-dom';

const LevellogItem = ({ levellog }) => {
  const { author, title } = levellog;

  return (
    <Card size="SMALL">
      <div css={ContentStyle}>
        <div css={DescriptionStyle}>
          <h3>{title}</h3>
        </div>
        <div css={[FlexStyle, FlexColumnStyle, AlignItemsEndStyle]}>
          <Link to={`/${author.username}`}>
            <ProfileChip imageSrc={author.imageUrl} cssProps={ProfileChipLocationStyle}>
              {author.nickname}
            </ProfileChip>
          </Link>
        </div>
      </div>
    </Card>
  );
};

export default LevellogItem;
