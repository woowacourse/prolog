import { useCallback, useState } from 'react';
import { useLocation } from 'react-router-dom';
import queryString from 'query-string';

const useFilterWithParams = () => {
  const query = queryString.parse(useLocation().search);

  const levelFilter = makeFilters(query.levels, 'levels');
  const missionFilter = makeFilters(query.missions, 'missions');
  const tagFilter = makeFilters(query.tags, 'tags');

  const [selectedFilter, setSelectedFilter] = useState('');
  const [selectedFilterDetails, setSelectedFilterDetails] = useState([
    ...levelFilter,
    ...missionFilter,
    ...tagFilter,
  ]);

  const [postQueryParams, setPostQueryParams] = useState({
    page: query.page ? query.page : 1,
  });

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  const onFilterChange = (value) => {
    setPostQueryParams({ ...postQueryParams, page: 1 });
    setSelectedFilterDetails(value);
  };

  const resetFilter = () => {
    setSelectedFilterDetails([]);
  };

  const onUnsetFilter = ({ filterType, filterDetailId }) => {
    const newFilters = selectedFilterDetails.filter(
      (filter) => !(filter.filterType === filterType && filter.filterDetailId === filterDetailId)
    );

    setPostQueryParams({ ...postQueryParams, page: 1 });
    setSelectedFilterDetails(newFilters);
  };

  const getFullParams = useCallback(() => {
    if (postQueryParams.page == 1) {
      delete postQueryParams.page
    }

    const pageParams = queryString.stringify(postQueryParams);
    const filterParams =
      selectedFilterDetails.length > 0
        ? selectedFilterDetails
            .map((filter) => `${filter.filterType}=${filter.filterDetailId}`)
            .join('&')
        : '';

    return `${pageParams}${filterParams ? `&${filterParams}` : ''}`;
  }, [postQueryParams, selectedFilterDetails]);

  return {
    postQueryParams,
    selectedFilter,
    setSelectedFilter,
    selectedFilterDetails,
    setSelectedFilterDetails,
    onSetPage,
    onUnsetFilter,
    onFilterChange,
    resetFilter,
    getFullParams,
  };
};

const makeFilters = (filters, filterType) => {
  if (!filters || !filterType) return [];

  if (typeof filters === 'string') {
    return [{ filterType, filterDetailId: Number(filters) }];
  }

  return [...filters].map((id) => ({ filterType, filterDetailId: Number(id) }));
};

export default useFilterWithParams;
