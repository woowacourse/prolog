import styled from '@emotion/styled';
import { COLOR } from '../../enumerations/color';

export const Root = styled.div`
  display: flex;
  flex-direction: column;

  padding-bottom: 28px;
`;

export const Top = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const Left = styled.div`
  display: flex;
  align-items: center;
  gap: 18px;

  & > a {
    display: flex;
    align-items: center;
    gap: 10px;
  }
`;

export const Logo = styled.img`
  width: 36px;
  height: 36px;
  border-radius: 12px;
`;

export const CreatedDate = styled.span`
  font-size: 12px;
  color: ${COLOR.LIGHT_GRAY_900};
`;

export const Right = styled.div`
  font-size: 14px;
  color: ${COLOR.LIGHT_GRAY_900};

  display: flex;
  gap: 10px;
`;
