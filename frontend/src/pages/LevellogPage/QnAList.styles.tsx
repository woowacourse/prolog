import styled from '@emotion/styled';
import { COLOR } from '../../constants';
import { FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';

export const S = {
  Container: styled.div`
    ${FlexStyle}
    ${FlexColumnStyle}
      margin: 3rem 0;
  `,
  SubTitle: styled.span`
    font-size: large;
    font-weight: bold;
  `,
  QnAsWrapper: styled.div`
    ${FlexStyle}
    ${FlexColumnStyle}
      gap: 3rem;
    margin: 1rem 0;
  `,
  QnA: styled.div`
    ${FlexStyle}
    ${FlexColumnStyle}
      border: 1px solid ${COLOR.LIGHT_GRAY_200};
    border-radius: 7px;
  `,
  Question: styled.div`
    padding: 1rem;
    background-color: ${COLOR.LIGHT_BLUE_300};
    font-weight: bold;
    border-radius: 4px 4px 0 0;
  `,
  Answer: styled.div`
    padding: 1rem;
    border-radius: 0 0 4px 4px;
  `,
};
