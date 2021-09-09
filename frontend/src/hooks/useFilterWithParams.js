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
    page: query.page ?? 1,
    size: query.size ?? 10,
    direction: query.direction ?? 'desc',
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

    setSelectedFilterDetails(newFilters);
  };

  const getFullParams = useCallback(() => {
    const pageParams = queryString.stringify(postQueryParams);
    const filterParams = selectedFilterDetails
      .map((filter) => `${filter.filterType}=${filter.filterDetailId}`)
      .join('&');

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

  return [...filters].map((id) => ({ filterType, filterDetailId: Number(id) }));
};

export default useFilterWithParams;
