import { css } from '@emotion/react';
import { COLOR } from '../../enumerations/color';

export const DefaultScrapButtonStyle = css`
  flex-direction: column;
  padding: 0;
  width: fit-content;
  font-size: 1.4rem;

  background-color: transparent;
  color: ${COLOR.BLACK_800};

  & > img {
    margin-right: 0;
    width: 2.4rem;
    height: 2.4rem;
  }
`;
