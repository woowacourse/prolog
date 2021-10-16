import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Category = styled.div`
  margin-right: 2rem;
  h4 {
    margin-bottom: 0.5rem;
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
  width: 40rem;
  height: 12rem;
  overflow-y: scroll;

  font-size: 1.4rem;

  position: relative;

  li {
    height: 3rem;
    display: grid;
    grid-template-columns: 4rem 25rem 5rem 6rem;

    justify-content: center;
    align-items: center;

    border-bottom: 1px solid ${COLOR.LIGHT_GRAY_400};

    > * {
      height: 100%;

      display: flex;
      align-items: center;

      :not(label) {
        justify-content: center;
      }

      :not(:last-child) {
        border-right: 1px solid ${COLOR.LIGHT_GRAY_400};
      }
    }
  }
`;

export const CategoryListTitle = styled.li`
  position: sticky;

  top: 0;
  left: 0;

  background-color: ${COLOR.LIGHT_GRAY_100};
`;

export const CategoryItem = styled.li<{
  checked: boolean;
  currentCategory: number;
  chipColor: string;
}>`
  list-style: none;

  label {
    padding-left: 1rem;
    display: flex;

    align-items: center;
    cursor: pointer;

    text-align: left;
  }

  span {
    width: 1rem;
    height: 1rem;

    margin-right: 1rem;

    background-color: ${({ chipColor }) => chipColor || 'grey'};

    border-radius: 0.2rem;
  }

  :hover,
  :focus {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }

  input[type='radio'] {
    width: 0;
    height: 0;
  }

  input[type='number'] {
    width: 80%;
    height: 80%;

    background-color: ${COLOR.LIGHT_GRAY_100};
    border: none;
  }

  p {
    margin: 0;
  }

  opacity: ${({ checked, currentCategory }) => (currentCategory === -1 ? 1 : checked ? 1 : 0.5)};
`;
