import CreatableSelect from 'react-select/creatable';

import COLOR from '../../constants/color';

const selectStyles = {
  container: (styles) => ({
    ...styles,
    fontSize: '1.4rem',
  }),
  control: (styles) => ({
    ...styles,
    backgroundColor: 'white',
    outline: 'none',
    border: '0',
    minHeight: '2.4rem',
    cursor: 'pointer',
  }),
  indicatorsContainer: (styles) => ({ ...styles, display: 'none' }),
  valueContainer: (styles) => ({ ...styles, padding: '0' }),
  multiValue: (styles) => ({
    ...styles,
    backgroundColor: COLOR.LIGHT_GRAY_300,
    paddingLeft: '0.3rem',
    borderRadius: '0.6rem',
  }),
  multiValueLabel: (styles) => ({ ...styles, paddingRight: '0.2rem' }),
  multiValueRemove: (styles) => ({
    ...styles,
    borderRadius: '0.6rem',
    paddingLeft: '0.2rem',
    paddingRight: '0.2rem',
    cursor: 'pointer',
  }),
  menu: (styles) => ({ ...styles, fontSize: '1.4rem', fontColor: COLOR.DARK_GRAY_900 }),
};

const CreatableSelectBox = ({ isMulti = true, options, placeholder, onChange, ...props }) => (
  <CreatableSelect
    {...props}
    isMulti={isMulti}
    options={options}
    placeholder={placeholder}
    onChange={onChange}
    styles={selectStyles}
    theme={(theme) => ({ ...theme, colors: { ...theme.colors, primary: 'transparent' } })}
  />
);

export default CreatableSelectBox;
