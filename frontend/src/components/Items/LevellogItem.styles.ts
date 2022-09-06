import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { COLOR } from '../../enumerations/color';

export const CardStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;
  padding: 3rem;
  height: 20rem;

  :hover {
    transform: scale(1.005);
  }
`;

export const ContentStyle = css`
  display: flex;
  justify-content: space-between;
  height: 100%;
`;

export const DescriptionStyle = css`
  width: calc(100% - 12rem);
  display: flex;
  flex-direction: column;
  height: inherit;

  h3 {
    font-size: 2.8rem;
    color: ${COLOR.DARK_GRAY_900};
    font-weight: bold;

    height: 100%;

    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;

    @media screen and (max-width: 420px) {
      font-size: 2.5rem;
      height: calc(2.5rem * 4.5);
    }
  }
`;

export const ProfileChipLocationStyle = css`
  flex-shrink: 0;
  margin-left: 1rem;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

export const NoDefaultHoverLink = styled(Link)`
  :hover {
    font-weight: unset;
  }
`;
