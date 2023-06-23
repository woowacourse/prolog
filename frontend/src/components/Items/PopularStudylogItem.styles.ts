import { css } from '@emotion/react';
import MEDIA_QUERY from '../../constants/mediaQuery';
import { COLOR } from '../../enumerations/color';
import { hexToRgba } from '../../utils/styles';

const RANDOM_COLOR_PALLETS = [
  '#ff9797',
  '#ffcb20',
  '#bade95',
  '#a5e1e6',
  '#ff9ebb',
  '#74bcff',
  '#c886ce',
  '#9fece0',
  '#a1d9ed',
  '#b3a4d0',
  '#c3a9c9',
  '#f0ec85',
  '#eeb887',
  '#d19191',
  '#dddf95',
  '#d28ab1',
];

const getRandomColor = (id: number): string => {
  return RANDOM_COLOR_PALLETS[id % RANDOM_COLOR_PALLETS.length];
};

export const ContainerStyle = css`
  height: 28rem;

  margin: 0.2rem;

  border-radius: 1.6rem;

  box-shadow: 1px 1px 2px 0 ${hexToRgba(COLOR.BLACK_900, 0.4)};

  transition: transform ease-in-out 0.1s;
  :hover {
    transform: translateY(-1rem);
  }
  *:hover:not(h2) {
    font-weight: unset;
  }
`;

export const TopContainerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 0.7rem;

  width: 100%;
  height: 13rem;

  padding: 1.5rem 1.6rem 0 1.6rem;

  border-radius: 1.6rem;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;

  position: relative;

  overflow: hidden;

  h2 {
    height: 100%;
    font-size: 1.7rem;
    line-height: 1.4;
    font-weight: bold;

    line-break: auto;

    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  span {
    font-size: 1.2rem;
  }
`;

export const TitleLink = css`
  height: 100%;
`;

export const BottomContainerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 100%;
  height: 15rem;

  padding: 1rem 1.6rem 1rem;

  background-color: white;
  border-radius: inherit;
  border-top-right-radius: 0;
  border-top-left-radius: 0;

  position: relative;

  span {
    font-size: 1.2rem;
  }

  > :first-child {
    margin-bottom: auto;
  }
`;

export const UserReactionIconStyle = css`
  flex-shrink: 0;
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.4rem;
  margin-right: 0.6rem;

  & > svg {
    margin-right: 0.25rem;
  }

  span {
    vertical-align: top;
  }
`;

export const ProfileAreaStyle = css`
  width: fit-content;

  span {
    margin-left: 1rem;

    font-size: 1.4rem;
  }

  img {
    width: 2.7rem;
    height: 2.7rem;

    border-radius: 3rem;

    z-index: 10;
  }
`;

export const ContentsAreaStyle = css`
  > a > div {
    font-size: 1.4rem;

    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;

    ${MEDIA_QUERY.sm} {
      -webkit-line-clamp: 2;
    }
  }
`;

export const TagContainerStyle = css`
  display: flex;
  flex-wrap: wrap;
  gap: 1.4rem;

  height: 24px;

  overflow: hidden;
`;

export const getRandomBgColorStyle = (id: number) =>
  css`
    background-color: ${getRandomColor(id)};
  `;
