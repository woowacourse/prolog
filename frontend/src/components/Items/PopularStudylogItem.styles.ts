import { css } from '@emotion/react';

import { COLOR } from '../../enumerations/color';
import { getRandomColor } from '../../service/studylogs/popularStudylogs';
import { getSize, hexToRgba } from '../../utils/styles';

export const ContainerStyle = css`
  width: 24rem;
  height: 32rem;

  border-radius: 1.6rem;

  box-shadow: 1px 1px 2px 0 ${hexToRgba(COLOR.BLACK_900, 0.4)};
`;

export const TopContainerStyle = css`
  width: 100%;
  height: 17rem;

  padding: 1rem 1.6rem 4rem;

  border-radius: 1.6rem;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;

  position: relative;

  overflow: hidden;

  :hover {
    h2 {
      text-decoration: underline;
    }
  }

  h2 {
    font-size: 2rem;
    line-height: 1.4;
    font-weight: bold;
    color: black;

    line-break: anywhere;

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

export const DateAreaStyle = css`
  position: absolute;
  bottom: 1rem;
  right: 1rem;
`;

export const BottomContainerStyle = css`
  width: 100%;
  height: 15rem;

  padding: 1.8rem 1.6rem 1rem;

  background-color: white;
  border-radius: inherit;
  border-top-right-radius: 0;
  border-top-left-radius: 0;

  position: relative;

  span {
    font-size: 1.2rem;
  }
`;

export const UserReactionIconStyle = css`
  flex-shrink: 0;
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.4rem;
  margin-top: 0.5rem;

  & > svg {
    margin-right: 0.25rem;
  }

  span {
    vertical-align: top;
  }
`;

export const ProfileAreaStyle = css`
  :hover {
    span {
      text-decoration: underline;
    }

    img {
      opacity: 0.8;
    }
  }

  div {
    position: absolute;
    left: 1rem;
    top: 0;

    transform: translateY(-80%);

    span {
      padding-bottom: 1rem;
      margin-left: 1rem;

      font-size: 1.6rem;
    }

    img {
      width: 4.8rem;
      height: 4.8rem;

      border-radius: 1.6rem;

      z-index: 10;
    }
  }
`;

export const ContentsAreaStyle = css`
  height: 100%;

  > a > div {
    height: calc(1.4rem * 4.5);

    font-size: 1.4rem;

    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  :hover {
    > a {
      text-decoration: underline;
    }
  }
`;

export const getHorizontalGapStyle = (gap: number | string) => css`
  > *:not(:last-child) {
    margin-right: ${getSize(gap)};
  }
`;

export const getRandomBgColorStyle = (id: number) =>
  css`
    background-color: ${getRandomColor(id)};
  `;
