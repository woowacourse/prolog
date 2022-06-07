/** @jsxImportSource @emotion/react */

import { Container, BadgeContainer, TooltipText } from './BadgeList.styles';
import { ReactComponent as PassionBadge } from '../../assets/images/badge-1-level2.svg';
import { ReactComponent as ComplimentBadge } from '../../assets/images/badge-2-level2.svg';

const BADGE_OBJ = {
  PASSION_KING: {
    imgSrc: PassionBadge,
    description: '와우!🕺 level2 동안 7개 이상의 글을 작성하신 당신은, 열정왕!🔥',
  },
  COMPLIMENT_KING: {
    imgSrc: ComplimentBadge,
    description: '칭찬왕님~🥰 level2 동안 15번 이상이나 좋아요를 눌러주셔서 고마워요.',
  },
};

interface BadgeListProps {
  badgeList: [keyof typeof BADGE_OBJ];
}

export const dummyBadgeList = ['PASSION_KING', 'COMPLIMENT_KING'];

const BadgeList = ({ badgeList }: BadgeListProps) => {
  return (
    <Container>
      {badgeList.map((name) => {
        const Badge = BADGE_OBJ[name]['imgSrc'];

        return (
          <BadgeContainer key={name}>
            <Badge aria-label={name} />
            <TooltipText>{BADGE_OBJ[name].description}</TooltipText>
          </BadgeContainer>
        );
      })}
    </Container>
  );
};

export default BadgeList;
