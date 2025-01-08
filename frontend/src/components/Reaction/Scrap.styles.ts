import { css } from '@emotion/react';
import { COLOR } from '../../enumerations/color';

export const DefaultScrapButtonStyle = css`
  flex-direction: column;
  padding: 0;
  width: fit-content;
  font-size: 1.4rem;
  height: inherit;

  background-color: transparent;
  color: ${COLOR.DARK_GRAY_400};

  & > img {
    margin-right: 0;
    width: 2.4rem;
  }
`;
