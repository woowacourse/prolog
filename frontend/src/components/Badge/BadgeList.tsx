/** @jsxImportSource @emotion/react */

import { Container, BadgeContainer, TooltipText } from './BadgeList.styles';
import { ReactComponent as PassionBadge } from '../../assets/images/badge-1-level2.svg';
import { ReactComponent as ComplimentBadge } from '../../assets/images/badge-2-level2.svg';

const BADGE_OBJ = {
  PASSION_KING: {
    svgComponent: PassionBadge,
    description: 'μμ°!πΊ level2 λμ 7κ° μ΄μμ κΈμ μμ±νμ  λΉμ μ, μ΄μ μ!π₯',
  },
  COMPLIMENT_KING: {
    svgComponent: ComplimentBadge,
    description: 'μΉ­μ°¬μλ~π₯° level2 λμ 15λ² μ΄μμ΄λ μ’μμλ₯Ό λλ¬μ£Όμμ κ³ λ§μμ.',
  },
};

interface BadgeListProps {
  badgeList: [{ name: keyof typeof BADGE_OBJ }];
}

const BadgeList = ({ badgeList }: BadgeListProps) => {
  return (
    <Container>
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
    </Container>
  );
};

export default BadgeList;
