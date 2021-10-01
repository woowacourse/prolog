const onToggleCheckbox = (checkboxList, item) => {
  const checkboxIds = checkboxList.map((checkItem) => checkItem.id);

  if (checkboxIds.includes(item.id)) {
    const index = checkboxIds.indexOf(item.id);

    return [...checkboxList.slice(0, index), ...checkboxList.slice(index + 1)];
  } else {
    return [item, ...checkboxList];
  }
};

export { onToggleCheckbox };
