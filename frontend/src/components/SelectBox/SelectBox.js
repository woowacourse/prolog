import styled from '@emotion/styled';
import PropTypes from 'prop-types';
import { useEffect, useRef, useState } from 'react';

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
    border: solid 14px transparent;
    border-top-color: #222;
  }
`;

const Select = styled.select`
  display: block;
  padding: 1.4rem 4rem;
  width: 100%;
  min-height: 5rem;
  font-size: 2.4rem;
  font-family: inherit;

  background-color: #fff;
  outline: none;
  border: 2px solid #153147;
  border-radius: 22px;
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
  border-radius: 22px;
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
  padding: 1.4rem 4rem;
  min-height: 5rem;
  font-size: 2.4rem;
  cursor: pointer;
  background-color: #fff;

  &:hover {
    background-color: #e0e0e0;
  }

  &:first-of-type:hover {
    border-top-right-radius: 22px;
    border-top-left-radius: 22px;
  }

  &:last-child:hover {
    border-bottom-right-radius: 22px;
    border-bottom-left-radius: 22px;
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

const SelectBox = ({ options }) => {
  const [selectItems, setSelectItems] = useState(null);
  const [selectedValue, setSelectedValue] = useState('');

  const $label = useRef(null);
  const $selectorContainer = useRef(null);

  useEffect(() => {
    document.addEventListener('click', onCloseCategoryList);

    return () => {
      document.removeEventListener('click', onCloseCategoryList);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectItems]);

  useEffect(() => {
    if (!$selectorContainer.current) return;

    const scrollY =
      ($selectorContainer.current.scrollHeight / options.length) * options.indexOf(selectedValue);

    $selectorContainer.current.scroll({ top: scrollY, behavior: 'smooth' });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectItems]);

  const getSelectItems = () => (
    <SelectItems ref={$selectorContainer}>
      {options.map((option) => {
        const onSelectItem = (e) => {
          e.stopPropagation();

          setSelectedValue(option);
          setSelectItems(null);
        };

        return (
          <SelectItem key={option} onMouseDown={onSelectItem} isSelected={option === selectedValue}>
            {option}
          </SelectItem>
        );
      })}
    </SelectItems>
  );

  const onCreateCategoryList = (e) => {
    e.preventDefault();

    setSelectItems(getSelectItems());
  };

  const onCloseCategoryList = (e) => {
    if (!selectItems) return;
    e.preventDefault();

    if (!$label.current?.contains(e.target)) {
      setSelectItems(null);
    }
  };

  return (
    <Label ref={$label} onMouseDown={onCreateCategoryList}>
      <Select name="mission_subjects" value={selectedValue} readOnly>
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </Select>
      {selectItems}
    </Label>
  );
};

SelectBox.defaultProps = {
  options: ['주제가 등록되지 않았습니다.'],
};

SelectBox.propTypes = {
  options: PropTypes.array.isRequired,
};

export default SelectBox;
