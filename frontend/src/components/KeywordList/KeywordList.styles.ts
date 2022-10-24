import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR } from '../../enumerations/color';

export const Root = styled.main`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const TopKeywordButton = css`
  background-color: ${COLOR.LIGHT_BLUE_900};
`;

export const SecondKeywordButton = css`
  background-color: ${COLOR.LIGHT_BLUE_600};
`;

export const ThirdKeywordButton = css`
  background-color: ${COLOR.LIGHT_BLUE_300};
`;

export const ChildrenKeywordListContainer = styled.div`
  display: flex;
  gap: 30px;
`;

export const SecondButtonWrapper = styled.div`
  display: flex;
  flex-grow: 1;
`;

export const ThirdButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 10px;
`;
