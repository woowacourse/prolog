import styled from '@emotion/styled';

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
    border-top-color: #222;
  }
`;

const Select = styled.select`
  display: block;
  padding: 1.2rem 2rem;
  width: 100%;
  min-height: 3rem;
  font-size: 1.6rem;
  font-family: inherit;

  background-color: #fff;
  outline: none;
  border: 2px solid #153147;
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
  color: #153147;

  background-color: #e0e0e0;
  border-radius: 1.6rem;
  border: 2px solid #153147;

  & {
    ::-webkit-scrollbar {
      width: 0;
    }

    ::-webkit-scrollbar-track {
      margin: 1rem 0;
      border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb {
      background-color: #153147;
      border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb:hover {
      background-color: #153147;
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
  background-color: #fff;

  &:hover {
    background-color: #e0e0e0;
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
    `background-color: #A9CBE5; 
    
    &:first-of-type {
      border-top-left-radius: 22px;
    }

    &:last-child {
      border-bottom-left-radius: 22px;
    }`}
`;

export { Label, Select, SelectItems, SelectItem };
