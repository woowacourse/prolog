import { DropdownMenu } from '..';
import checkIcon from '../../assets/images/check.png';
import { Container, FilterDetail, ResetFilter, CheckIcon } from './FilterList.styles';

const FilterList = ({
  selectedFilter,
  setSelectedFilter,
  filters,
  selectedFilterDetails,
  setSelectedFilterDetails,
  isVisibleResetFilter,
  onResetFilter,
}) => {
  const closeDropdown = (event) => {
    if (event.target === event.currentTarget) {
      setSelectedFilter('');
    }
  };

  const findFilterItem = (key, id) =>
    selectedFilterDetails.find(
      (filterItem) => filterItem.filterType === key && filterItem.filterDetailId === id
    );

  const toggleFilterDetails = (filterType, filterDetailId, name) => {
    const targetFilterItem = { filterType, filterDetailId, name };
    const isExistFilterItem = findFilterItem(filterType, filterDetailId);

    if (isExistFilterItem) {
      setSelectedFilterDetails(selectedFilterDetails.filter((item) => item !== isExistFilterItem));
    } else {
      setSelectedFilterDetails([...selectedFilterDetails, targetFilterItem]);
    }
  };

  const getFilteredFiltersByLevel = () => {
    // 선택된 levels 목록 조회
    const selectedLevels = selectedFilterDetails
      .filter((selectedFilter) => selectedFilter.filterType === 'levels')
      .map((selectedFilter) => selectedFilter.filterDetailId);

    // 선택된 레벨의 미션이 제외된 미션 목록
    const filteredMissions = selectedLevels.length
      ? filters.missions &&
        filters.missions.filter((mission) => selectedLevels.includes(mission.level.id))
      : filters.missions;

    const filteredFilters = { ...filters };
    filteredFilters.missions = filteredMissions;

    return filteredFilters;
  };

  return (
    <Container onClick={closeDropdown} isDropdownToggled={selectedFilter}>
      {Object.entries(getFilteredFiltersByLevel()).map(([key, value]) => (
        <div key={key}>
          <button onClick={() => setSelectedFilter(key)}>{key}</button>
          {selectedFilter === key && (
            <DropdownMenu>
              {/* 검색 UI
              <li>
                <input type="search" placeholder="filter project" />
              </li> */}
              {value.map(({ id, name }) => (
                <li key={id} onClick={() => toggleFilterDetails(key, id, name)}>
                  <FilterDetail>
                    <span>{name}</span>
                    <CheckIcon
                      src={checkIcon}
                      alt="선택된 필터 표시"
                      checked={findFilterItem(key, id)}
                    />
                  </FilterDetail>
                </li>
              ))}
            </DropdownMenu>
          )}
        </div>
      ))}
      {isVisibleResetFilter && <ResetFilter onClick={onResetFilter}>필터 초기화 ⟳</ResetFilter>}
    </Container>
  );
};

export default FilterList;
