import PropTypes from 'prop-types';
import { useEffect, useRef } from 'react';

import useCustomSelectBox from '../../hooks/useCustomSelectBox';
import useScrollToSelected from '../../hooks/useScrollToSelected';
import { Label, Select, SelectItemList, SelectItem } from './SelectBox.styles';

const SelectBox = ({
  options,
  selectedOption,
  setSelectedOption,
  width,
  maxHeight,
  title,
  name,
  size,
}) => {
  const $label = useRef(null);
  const $selectorContainer = useRef(null);

  const [isSelectBoxOpened, setIsSelectBoxOpened] = useCustomSelectBox({ targetRef: $label });

  useScrollToSelected({
    container: $selectorContainer,
    dependency: isSelectBoxOpened,
    options,
    selectedOption,
  });

  useEffect(() => {
    if (options.length > 0) {
      setSelectedOption(options[0]?.name);
    }
  }, [options]);

  const onSelectItem = (event, option) => {
    event.stopPropagation();

    option ? setSelectedOption(option) : setSelectedOption(event.target.value);
    setIsSelectBoxOpened(false);
  };

  const onOpenCustomSelectBox = (event) => {
    event.preventDefault();

    setIsSelectBoxOpened(true);
  };

  return (
    <Label aria-label={title} ref={$label} onMouseDown={onOpenCustomSelectBox} width={width}>
      <Select name={name} onChange={onSelectItem} value={selectedOption} fontSize={size}>
        {options.map((option) => (
          <option key={option.id} value={option.name}>
            {option.name}
          </option>
        ))}
      </Select>

      {isSelectBoxOpened && (
        <SelectItemList ref={$selectorContainer} maxHeight={maxHeight}>
          {options.map((option) => (
            <SelectItem
              key={option.id}
              onMouseDown={(event) => onSelectItem(event, option.name)}
              isSelected={option.name === selectedOption}
              fontSize={size}
            >
              {option.name}
            </SelectItem>
          ))}
        </SelectItemList>
      )}
    </Label>
  );
};

SelectBox.propTypes = {
  options: PropTypes.array.isRequired,
  selectedOption: PropTypes.string.isRequired,
  setSelectedOption: PropTypes.func.isRequired,
  title: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  width: PropTypes.string,
  maxHeight: PropTypes.string,
};

SelectBox.defaultProps = {
  options: [],
  selectedOption: '',
  width: '100%',
  maxHeight: '20rem',
};

export default SelectBox;
