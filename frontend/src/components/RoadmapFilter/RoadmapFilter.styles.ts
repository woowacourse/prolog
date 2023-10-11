import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import MEDIA_QUERY from '../../constants/mediaQuery';

export const Container = styled.div`
  background-color: ${COLOR.LIGHT_GRAY_50};

  margin-bottom: 30px;

  border: 1px solid ${COLOR.DARK_GRAY_400};
  border-radius: 1.4rem;
  padding: 0 3.2rem;
  display: flex;
  font-size: 1.4rem;
  height: 46px;
  justify-content: space-between;
  align-items: center;
`;

export const FilterContainer = styled.div`
  display: flex;

  & > div:not(:last-child) {
    margin-right: 3.2rem;

    ${MEDIA_QUERY.xs} {
      margin-right: 2rem;
    }
  }

  ${MEDIA_QUERY.sm} {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
  }

  & button {
    display: flex;
    align-items: center;
    height: 100%;
    text-align: center;
    color: ${COLOR.DARK_GRAY_500};

    ::after {
      content: '';
      width: 0;
      height: 0;
      transform: translateY(50%);
      margin-left: 0.2rem;
      border-top: 0.5rem solid ${COLOR.DARK_GRAY_500};
      border-bottom: 0.5rem solid transparent;
      border-left: 0.5rem solid transparent;
      border-right: 0.5rem solid transparent;
    }
  }
`;

export const DropdownStyle = css`
  padding-top: 0;
`;

export const ResetFilterButton = styled.button`
  cursor: pointer;
`;
