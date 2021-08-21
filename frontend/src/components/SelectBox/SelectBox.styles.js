import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Label = styled.label`
  position: relative;
  display: inline-block;
  width: ${({ width }) => width ?? `${width}`};

  &::after {
    content: '';
    display: inline-block;
    position: absolute;
    right: 2rem;
    top: 45%;

    border: solid 0.8rem transparent;
    border-top-color: ${COLOR.BLACK_600};
    border-radius: 0.125rem;
    cursor: pointer;
  }
`;

const Select = styled.select`
  padding: 1rem 2rem;
  width: 100%;

  font-size: 1.6rem;
  line-height: 1.5;
  font-family: inherit;

  background-color: ${COLOR.WHITE};
  border: 2px solid ${COLOR.DARK_BLUE_800};
  border-radius: 1rem;
  outline: none;
  cursor: pointer;

  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
`;

const SelectItems = styled.ul`
  position: absolute;
  top: 0;
  width: 100%;
  max-height: ${({ maxHeight }) => maxHeight ?? `${maxHeight}`};
  overflow-y: auto;

  list-style: none;
  z-index: 1;

  color: ${COLOR.DARK_BLUE_800};
  background-color: ${COLOR.LIGHT_GRAY_200};
  border: 2px solid ${COLOR.DARK_BLUE_800};
  border-radius: 1rem;

  & {
    ::-webkit-scrollbar {
      width: 1rem;
    }

    ::-webkit-scrollbar-track {
      margin: 1rem 0;
      border-radius: 1rem;
    }

    ::-webkit-scrollbar-thumb {
      background-color: ${COLOR.DARK_BLUE_800};
      border-radius: 1rem;
    }
  }
`;

const SelectItem = styled.li`
  display: flex;
  align-items: center;
  padding: 1rem 2rem;
  font-size: 1.6rem;
  cursor: pointer;
  background-color: ${COLOR.WHITE};

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_200};
  }

  &:first-of-type:hover {
    border-top-right-radius: 1rem;
    border-top-left-radius: 1rem;
  }

  &:last-child:hover {
    border-bottom-right-radius: 1rem;
    border-bottom-left-radius: 1rem;
  }

  ${({ isSelected }) =>
    isSelected &&
    `background-color: ${COLOR.LIGHT_BLUE_200};
    
    &:first-of-type {
      border-top-left-radius: 22px;
    }

    &:last-child {
      border-bottom-left-radius: 22px;
    }`}
`;

export { Label, Select, SelectItems, SelectItem };
