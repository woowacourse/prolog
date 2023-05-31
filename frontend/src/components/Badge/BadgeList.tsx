/** @jsxImportSource @emotion/react */

import { Container, BadgeContainer, TooltipText, NoBadgeMessage } from './BadgeList.styles';
import { ReactComponent as PassionBadge } from '../../assets/images/badge-1-level2.svg';
import { ReactComponent as ComplimentBadge } from '../../assets/images/badge-2-level2.svg';

const BADGE_OBJ = {
  PASSION_KING: {
    svgComponent: PassionBadge,
    description: '와우!🕺 level2 동안 7개 이상의 글을 작성하신 당신은, 열정왕!🔥',
  },
  COMPLIMENT_KING: {
    svgComponent: ComplimentBadge,
    description: '칭찬왕님~🥰 level2 동안 15번 이상이나 좋아요를 눌러주셔서 고마워요.',
  },
};

interface BadgeListProps {
  badgeList: { name: keyof typeof BADGE_OBJ }[];
}

const BadgeList = ({ badgeList }: BadgeListProps) => {
  return (
    <Container>
      {badgeList.length === 0 ? (
        <NoBadgeMessage>획득한 베지가 없습니다 🥲</NoBadgeMessage>
      ) : (
        <>
          {badgeList.map(({ name }) => {
            if (!BADGE_OBJ[name]) return false;

            const { svgComponent: Badge, description } = BADGE_OBJ[name];

            return (
              <BadgeContainer key={name}>
                <Badge aria-label={name} />
                <TooltipText>{description}</TooltipText>
              </BadgeContainer>
            );
          })}
        </>
      )}
    </Container>
  );
};

export default BadgeList;
