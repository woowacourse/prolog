import PropTypes from 'prop-types';
import { useRef } from 'react';

import useCustomSelectBox from '../../hooks/useCustomSelectBox';
import useScrollToSelected from '../../hooks/useScrollToSelected';
import { Label, Select, SelectItems, SelectItem } from './SelectBox.styles';

const SelectBox = ({
  options,
  selectedOption,
  setSelectedOption,
  width,
  maxHeight,
  title,
  name,
}) => {
  const $label = useRef(null);
  const $selectorContainer = useRef(null);

  const [selectItems, setSelectItems] = useCustomSelectBox({ targetRef: $label });
  useScrollToSelected({
    container: $selectorContainer,
    dependency: selectItems,
    options,
    selectedOption,
  });

  const onSelectItem = (event, option) => {
    event.stopPropagation();

    option ? setSelectedOption(option) : setSelectedOption(event.target.value);
    setSelectItems(false);
  };

  const onOpenCustomSelectBox = (event) => {
    event.preventDefault();

    setSelectItems(getSelectItems());
  };

  const getSelectItems = () => (
    <SelectItems ref={$selectorContainer} maxHeight={maxHeight}>
      {options.map((option) => (
        <SelectItem
          key={option}
          onMouseDown={(event) => onSelectItem(event, option)}
          isSelected={option === selectedOption}
        >
          {option}
        </SelectItem>
      ))}
    </SelectItems>
  );

  return (
    <Label aria-label={title} ref={$label} onMouseDown={onOpenCustomSelectBox} width={width}>
      <Select name={name} onChange={onSelectItem} value={selectedOption}>
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

SelectBox.propTypes = {
  options: PropTypes.array.isRequired,
  selectedOption: PropTypes.string,
  setSelectedOption: PropTypes.func,
  title: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  width: PropTypes.string,
  maxHeight: PropTypes.string,
};

SelectBox.defaultProps = {
  options: ['주제가 등록되지 않았습니다.'],
  width: '100%',
  maxHeight: '20rem',
};

export default SelectBox;
