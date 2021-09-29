const filterOnlyNewList = (list, conditionList) => {
  return list.filter((item) => !conditionList.map((filterItem) => filterItem.id).includes(item.id));
};

const filterIds = (list) => {
  return list.map((item) => item.id);
};

export { filterOnlyNewList, filterIds };
