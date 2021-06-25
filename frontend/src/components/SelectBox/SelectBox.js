import PropTypes from 'prop-types';
import { useEffect, useRef, useState } from 'react';
import { Label, Select, SelectItems, SelectItem } from './SelectBox.styles';

const SelectBox = ({ options, selectedOption, setSelectedOption }) => {
  const [selectItems, setSelectItems] = useState(null);

  const $label = useRef(null);
  const $selectorContainer = useRef(null);

  const onCreateOptionList = (e) => {
    e.preventDefault();

    setSelectItems(getSelectItems());
  };

  const onCloseOptionList = (e) => {
    if (!selectItems) return;
    e.preventDefault();

    if (!$label.current?.contains(e.target)) {
      setSelectItems(null);
    }
  };

  useEffect(() => {
    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectItems]);

  useEffect(() => {
    if (!$selectorContainer.current) return;

    const scrollY =
      ($selectorContainer.current.scrollHeight / options.length) * options.indexOf(selectedOption);

    $selectorContainer.current.scroll({ top: scrollY, behavior: 'smooth' });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectItems]);

  const getSelectItems = () => (
    <SelectItems ref={$selectorContainer}>
      {options.map((option) => {
        const onSelectItem = (e) => {
          e.stopPropagation();

          setSelectedOption(option);
          setSelectItems(null);
        };

        return (
          <SelectItem
            key={option}
            onMouseDown={onSelectItem}
            isSelected={option === selectedOption}
          >
            {option}
          </SelectItem>
        );
      })}
    </SelectItems>
  );

  return (
    <Label ref={$label} onMouseDown={onCreateOptionList}>
      <Select name="mission_subjects" value={selectedOption} readOnly>
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
