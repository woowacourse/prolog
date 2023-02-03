import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { COLOR } from '../../enumerations/color';

export const CardStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;
  padding: 3rem;
  height: 15rem;

  :hover {
    transform: scale(1.02);
  }
`;

export const ContentStyle = css`
  display: flex;
  justify-content: space-between;
  height: 100%;
`;

export const ProfileChipLocationStyle = css`
  flex-shrink: 0;
  margin-left: 1rem;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

export const ContentsAreaStyle = css`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 80%;
  height: 15rem;

  padding: 1rem 1.6rem 1rem;

  span {
    font-size: 1.2rem;
  }

  > div {
    height: calc(1.4rem * 4.5);

    font-size: 1.4rem;

    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;

export const NoDefaultHoverLink = styled(Link)`
  :hover {
    font-weight: unset;
  }
`;
