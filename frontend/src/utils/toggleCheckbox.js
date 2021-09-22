const onToggleCheckbox = (checkboxList, id) => {
  if (checkboxList.includes(id)) {
    const index = checkboxList.indexOf(id);

    return [...checkboxList.slice(0, index), ...checkboxList.slice(index + 1)];
  } else {
    return [...checkboxList, id];
  }
};

export { onToggleCheckbox };
