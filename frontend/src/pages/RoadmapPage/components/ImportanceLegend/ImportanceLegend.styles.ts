import styled from '@emotion/styled';
import { HSL, hsl } from '../../colors';

export const Container = styled.div`
  display: flex;
  align-items: center;
  font-size: 10px;
  column-gap: 15px;
  margin-bottom: 20px;
`;

export const LegendItemList = styled.ul`
  display: flex;
  align-items: center;
  list-style: none;
  height: 16px;
  border-radius: 4px;
  overflow: hidden;
  border: 1px solid #bbbbbb;
`;

export const LegendItem = styled.li<{ $color: HSL }>`
  width: 40px;
  height: 100%;
  background: ${({ $color }) => hsl($color)};
`;
