const filterList = (list, conditionList) => {
  return list.filter((item) => !conditionList.map((filterItem) => filterItem.id).includes(item.id));
};

export { filterList };
