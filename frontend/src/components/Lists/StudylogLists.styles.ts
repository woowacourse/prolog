import { css } from '@emotion/react';
import { COLOR } from '../../constants';

export const CardStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;
  padding: 3rem;
  height: 20rem;

  &:hover {
    transform: scale(1.005);
  }
`;

export const ContentStyle = css`
  display: flex;
  justify-content: space-between;
  height: 100%;
`;

export const DescriptionStyle = css`
  display: flex;
  flex-direction: column;
  height: inherit;

  h3 {
    font-size: 2.8rem;
    color: ${COLOR.DARK_GRAY_900};
    font-weight: bold;
  }
`;

export const MissionStyle = css`
  margin: 0;

  font-size: 1.6rem;
  color: ${COLOR.DARK_GRAY_900};
`;

export const TagListStyle = css`
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
`;

export const ProfileChipLocationStyle = css`
  flex-shrink: 0;
  margin-left: 1rem;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;
