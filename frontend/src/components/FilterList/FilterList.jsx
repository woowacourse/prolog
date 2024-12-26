/** @jsxImportSource @emotion/react */

import { useState } from 'react';
import { DropdownMenu } from '..';
import SearchBar from '../SearchBar/SearchBar';
import {
  Container,
  ResetFilter,
  SearchBarStyle,
  SearchBarWrapper,
  DropdownStyle,
  FilterContainer,
} from './FilterList.styles';
import SelectedFilterList from './SelectedFilterList';

const FilterList = ({
  selectedFilter,
  setSelectedFilter,
  filters,
  selectedFilterDetails,
  setSelectedFilterDetails,
  isVisibleResetFilter,
  onResetFilter,
  css = null,
}) => {
  const [searchKeyword, setSearchKeyword] = useState('');

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
    <Container onClick={closeDropdown} isDropdownToggled={selectedFilter} css={css}>
      <FilterContainer>
      {Object.entries(getFilteredFiltersByLevel()).map(([key, values]) => (
        <div key={key}>
          <button
            className={"dropdown"}
            onClick={() => {
              setSelectedFilter(key);
              setSearchKeyword('');
            }}
          >
            {key}
          </button>
          {selectedFilter === key && (
            <DropdownMenu css={DropdownStyle}>
              {/*<SearchBarWrapper>*/}
              {/*  <SearchBar*/}
              {/*    css={SearchBarStyle}*/}
              {/*    onChange={(value) => setSearchKeyword(value)}*/}
              {/*    value={searchKeyword}*/}
              {/*  />*/}
              {/*</SearchBarWrapper>*/}
              <SelectedFilterList
                searchKeyword={searchKeyword}
                filterList={values}
                type={key}
                findFilterItem={findFilterItem}
                toggleFilterDetails={toggleFilterDetails}
              />
            </DropdownMenu>
          )}
        </div>
      ))}
      </FilterContainer>
      {isVisibleResetFilter && <ResetFilter onClick={onResetFilter}>필터 초기화 ⟳</ResetFilter>}
    </Container>
  );
};

export default FilterList;
