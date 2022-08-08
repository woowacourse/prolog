import styled from '@emotion/styled';
import { COLOR } from '../../enumerations/color';

export const CommentsContainer = styled.div`
  padding: 28px 12px 0;

  & > div + div {
    padding-top: 18px;
    border-top: 1px solid ${COLOR.LIGHT_GRAY_200};
  }
`;
