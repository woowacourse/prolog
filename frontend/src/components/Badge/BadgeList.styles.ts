import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Container = styled.div`
  width: 100%;
  display: flex;
  gap: 0.5em;
  flex-wrap: wrap;
  justify-content: center;
  margin: 1em 0;
`;

export const Badge = styled.img`
  width: 80px;
  height: 80px;
  border-radius: 2rem;
`;

export const BadgeContainer = styled.div`
  width: 90px;
  height: 90px;
  border-radius: 100px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${COLOR.WHITE};
  position: relative;

  &:hover p {
    visibility: visible;
  }
`;

export const TooltipText = styled.p`
  position: absolute;
  width: 150px;
  visibility: hidden;
  text-align: center;
  border-radius: 6px;
  padding: 10px;
  font-size: 1rem;
  z-index: 1;
  top: 120%;
  left: 10%;

  color: ${COLOR.WHITE};
  background-color: ${COLOR.BLACK_900};
  opacity: 0.7;
  word-break: keep-all;

  &::after {
    position: absolute;
    content: '';
    top: -14px;
    left: 35px;
    bottom: 100%;
    margin-left: -5px;
    border: 7px solid transparent;
    border-color: transparent transparent ${COLOR.BLACK_900} transparent;
  }
`;
