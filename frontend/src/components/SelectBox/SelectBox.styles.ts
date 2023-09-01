import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const fontSizeStyle = {
  SMALL: {
    fontSize: '1.3rem',
    lineHeight: '1.2',
  },
  MEDIUM: {
    fontSize: '1.6rem',
    lineHeight: '1.5',
  },
};

const Label = styled.label<{ width: string }>`
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

const Select = styled.select<{ fontSize: string }>`
  padding: 1rem 2rem;
  width: 100%;

  ${({ fontSize }) => fontSizeStyle[fontSize] || fontSizeStyle.MEDIUM};
  font-family: inherit;

  background-color: ${COLOR.WHITE};
  border: 1.7px solid ${COLOR.DARK_BLUE_800};
  border-radius: 1rem;
  outline: none;
  cursor: pointer;

  appearance: none;
`;

const SelectItemList = styled.ul<{ maxHeight: string }>`
  position: absolute;
  top: 0;
  width: 100%;
  max-height: ${({ maxHeight }) => maxHeight ?? `${maxHeight}`};
  overflow-y: auto;

  list-style: none;
  z-index: 7;

  color: ${COLOR.DARK_BLUE_800};
  background-color: ${COLOR.LIGHT_GRAY_200};
  border: 1.7px solid ${COLOR.DARK_BLUE_800};
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

const SelectItem = styled.li<{ fontSize: string; isSelected: boolean }>`
  display: flex;
  align-items: center;
  padding: 1rem 2rem;
  ${({ fontSize }) => fontSizeStyle[fontSize] || fontSizeStyle.MEDIUM};

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
      border-top-left-radius: 1rem;
    }

    &:last-child {
      border-bottom-left-radius: 1rem;
    }`}
`;

export { Label, Select, SelectItemList, SelectItem };
