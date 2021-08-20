import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Label = styled.label`
  display: inline-block;
  position: relative;
  width: 100%;

  &::after {
    content: '';
    display: inline-block;
    position: absolute;
    right: 2.6rem;
    top: 40%;

    border-radius: 2px;
    border: solid 0.8rem transparent;
    border-top-color: ${COLOR.BLACK_600};
  }
`;

const Select = styled.select`
  display: block;
  padding: 1.2rem 2rem;
  width: 100%;
  min-height: 3rem;
  font-size: 1.6rem;
  font-family: inherit;

  background-color: ${COLOR.WHITE};
  outline: none;
  border: 2px solid ${COLOR.DARK_BLUE_800};
  border-radius: 1.6rem;
  cursor: pointer;
  appearance: none;
`;

const SelectItems = styled.ul`
  position: absolute;
  width: 100%;
  max-height: 42rem;
  overflow-y: auto;
  top: 0%;
  list-style: none;
  margin: 0;
  z-index: 1;
  color: ${COLOR.DARK_BLUE_800};

  background-color: ${COLOR.DARK_GRAY_200};
  border-radius: 1.6rem;
  border: 2px solid ${COLOR.DARK_BLUE_800};

  & {
    ::-webkit-scrollbar {
      width: 0;
    }

    ::-webkit-scrollbar-track {
      margin: 1rem 0;
      border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb {
      background-color: ${COLOR.DARK_BLUE_800};
      border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb:hover {
      background-color: ${COLOR.DARK_BLUE_800};
    }
  }

  &:hover::-webkit-scrollbar {
    width: 1rem;
  }
`;

const SelectItem = styled.li`
  display: flex;
  align-items: center;
  padding: 1.2rem 2rem;
  min-height: 3rem;
  font-size: 1.6rem;
  cursor: pointer;
  background-color: ${COLOR.WHITE};

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_200};
  }

  &:first-of-type:hover {
    border-top-right-radius: 1.6rem;
    border-top-left-radius: 1.6rem;
  }

  &:last-child:hover {
    border-bottom-right-radius: 1.6rem;
    border-bottom-left-radius: 1.6rem;
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
