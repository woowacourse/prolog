import styled from '@emotion/styled';
import { COLOR } from '../../enumerations/color';
import { css } from '@emotion/react';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 3rem;
  padding-top: 2rem;
  border-top: 1px solid ${COLOR.LIGHT_GRAY_200};
`;

export const Top = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const Left = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  & > a {
    display: flex;
    align-items: center;
    gap: 10px;
  }
`;

export const Logo = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 12px;
  padding: 0.5rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_100};
`;

export const MemberName = styled.span`
  color: ${COLOR.DARK_GRAY_900};
  width: fit-content;
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

export const ButtonContainer = styled.div`
  display: flex;
  gap: 20px;
`;

export const ActionButton = styled.button`
  padding: 0.5rem 1rem;
  border-radius: 10px;
  border: 1px solid ${COLOR.LIGHT_GRAY_50};
  &:hover {
    border: 1px solid ${COLOR.LIGHT_GRAY_100};
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
`;

export const DeleteButton = styled.button`
  padding: 0.5rem 1rem;
  border-radius: 10px;
  &:hover {
    background-color: ${COLOR.RED_50};
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
`;
