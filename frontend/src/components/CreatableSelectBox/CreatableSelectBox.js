import CreatableSelect from 'react-select/creatable';

const selectStyles = {
  container: (styles) => ({
    ...styles,
    marginTop: '2rem',
  }),
  control: (styles) => ({
    ...styles,
    backgroundColor: 'white',
    outline: 'none',
    border: '0',
  }),
  indicatorsContainer: (styles) => ({ ...styles, display: 'none' }),
  valueContainer: (styles) => ({ ...styles, padding: '0' }),
  multiValue: (styles) => ({
    ...styles,
    backgroundColor: '#efefef',
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
  menu: (styles) => ({ ...styles, fontSize: '1.4rem', fontColor: '#333333' }),
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
