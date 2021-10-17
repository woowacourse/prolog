import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Category = styled.div`
  width: 28rem;

  h4 {
    margin-bottom: 0.5rem;
  }

  div {
    display: flex;
    justify-content: flex-end;
    align-items: flex-start;
  }

  button {
    padding: 0.2rem 1rem;
    margin-top: 0.5rem;

    font-size: 1.4rem;

    background-color: ${COLOR.LIGHT_GRAY_300};
    border-radius: 0.4rem;

    :hover,
    :focus {
      background-color: ${COLOR.LIGHT_GRAY_500};
    }

    :active {
    }
  }
`;

export const CategoryList = styled.ul`
  height: 14rem;
  overflow-y: scroll;

  border: 1px solid ${COLOR.LIGHT_GRAY_400};
  border-radius: 0.5rem;
  background-color: ${COLOR.LIGHT_GRAY_50};
`;

export const CategoryItem = styled.li<{
  checked: boolean;
  currentCategory: number;
  chipColor: string;
}>`
  list-style: none;

  label {
    display: flex;
    justify-content: left;
    align-items: center;
    cursor: pointer;
  }

  span {
    width: 1rem;
    height: 1rem;

    margin-right: 0.5rem;

    background-color: ${({ chipColor }) => chipColor || 'grey'};

    border-radius: 0.2rem;
  }

  :not(:last-child) {
    margin-bottom: 0.4rem;
  }

  :hover,
  :focus {
    transform: scale(1.01);
    font-weight: 600;
    opacity: 1;
  }

  input[type='radio'] {
    width: 0;
    height: 0;
  }

  opacity: ${({ checked, currentCategory }) => (currentCategory === -1 ? 1 : checked ? 1 : 0.5)};
`;
