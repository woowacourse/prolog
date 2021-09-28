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
  size,
}) => {
  const $label = useRef(null);
  const $selectorContainer = useRef(null);

  const currentOptions = ['선택해주세요', ...options];
  const [isSelectBoxOpened, setIsSelectBoxOpened] = useCustomSelectBox({ targetRef: $label });
  useScrollToSelected({
    container: $selectorContainer,
    dependency: isSelectBoxOpened,
    options,
    selectedOption,
  });

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
        {currentOptions.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </Select>
      {isSelectBoxOpened && (
        <SelectItems ref={$selectorContainer} maxHeight={maxHeight}>
          {options.map((option) => (
            <SelectItem
              key={option}
              onMouseDown={(event) => onSelectItem(event, option)}
              isSelected={option === selectedOption}
              fontSize={size}
            >
              {option}
            </SelectItem>
          ))}
        </SelectItems>
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
  options: ['주제가 등록되지 않았습니다.'],
  width: '100%',
  maxHeight: '20rem',
};

export default SelectBox;
