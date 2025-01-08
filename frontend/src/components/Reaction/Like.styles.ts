import { css } from '@emotion/react';
import { COLOR } from '../../constants';

export const LikeIconStyle = css`
  padding: 0;
  width: fit-content;
  font-size: 1.4rem;
  margin-left: 1rem;
  margin-right: 1rem;
  height: inherit;

  background-color: transparent;
  color: ${COLOR.DARK_GRAY_400};

  & > img {
    margin-right: 0;
    width: 2.4rem;
  }
`;
