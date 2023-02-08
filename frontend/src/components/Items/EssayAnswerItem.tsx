/** @jsxImportSource @emotion/react */

import {
  CardStyle,
  ContentsAreaStyle,
  ContentStyle,
  NoDefaultHoverLink,
  ProfileChipLocationStyle,
} from './EssayAnswerItem.styles';
import { AlignItemsEndStyle, FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import Card from '../Card/Card';
import ProfileChip from '../ProfileChip/ProfileChip';

const EssayAnswerItem = ({ essayAnswer }) => {
  const { author, answer } = essayAnswer;

  return (
    <Card size="EXTRA_SMALL" cssProps={CardStyle}>
      <div css={ContentStyle}>
        <div css={[ContentsAreaStyle]}>
              <div>{answer}</div>
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

export default EssayAnswerItem;
