/** @jsxImportSource @emotion/react */

import { Container, BadgeContainer, TooltipText } from './BadgeList.styles';
import { ReactComponent as passionBadge } from '../../assets/images/badge-1-level2.svg';
import { ReactComponent as praiseBadge } from '../../assets/images/badge-2-level2.svg';

interface BadgeListProps {
  badgeList: string[];
}

const BADGE_OBJ = {
  열정왕: {
    imgSrc: passionBadge,
    description: '와우!🕺 level2 동안 7개 이상의 글을 작성하신 당신은, 열정왕!🔥',
  },
  칭찬왕: {
    imgSrc: praiseBadge,
    description: '칭찬왕님~🥰 level2 동안 15번 이상이나 좋아요를 눌러주셔서 고마워요.',
  },
};

export const dummyBadgeList = ['열정왕', '칭찬왕'];

const BadgeList = ({ badgeList }: BadgeListProps) => {
  return (
    <Container>
      {badgeList.map((name) => {
        const Badge = BADGE_OBJ[name]['imgSrc'];

        return (
          <BadgeContainer key={name}>
            <Badge alt={name} />
            <TooltipText>{BADGE_OBJ[name].description}</TooltipText>
          </BadgeContainer>
        );
      })}
    </Container>
  );
};

export default BadgeList;
