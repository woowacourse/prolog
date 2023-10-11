import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { getCurriculums } from '../../apis/curriculum';
import DropdownMenu from '../DropdownMenu/DropdownMenu';
import { Container, DropdownStyle, FilterContainer } from './RoadmapFilter.styles';
import RoadmapSelectedFilter from './RoadmapSelectedFilter';

const filterKeyword = ['curriculumId', 'keywordId', 'quizIds', 'memberIds'];

const filterData = {
  curriculumId: [1, 2],
  keywordId: [1, 2, 3, 4, 5],
  quizIds: [29, 2, 3, 4],
  memberIds: [304, 287, 3],
};

interface RoadmapFilterProps {
  searchKeyword: string;
  setSearchKeyword: Dispatch<SetStateAction<string>>;
}

const RoadmapFilter = ({ searchKeyword, setSearchKeyword }: RoadmapFilterProps) => {
  const history = useHistory();
  const [params, setParams] = useState<Record<string, string>>({});

  const handleFilter = (filterName, filterItem) => {
    if (filterName === 'curriculumId' || filterName === 'keywordId') {
      if (!params[filterName] || params[filterName] !== filterItem) {
        setParams({ ...params, [filterName]: filterItem });
        return;
      }

      if (params[filterName] === filterItem) {
        setParams((prevParams) => {
          const updatedParams = { ...prevParams };
          delete updatedParams[filterName];

          return updatedParams;
        });
        return;
      }
    }

    if (filterName === 'quizIds' || filterName === 'memberIds') {
      if (!params[filterName]) {
        setParams({ ...params, [filterName]: String(filterItem) });
        return;
      }
      const idsList = params[filterName].split(',');
      if (!idsList.includes(String(filterItem))) {
        const ids = params[filterName] + ',' + String(filterItem);
        setParams({ ...params, [filterName]: ids });
        return;
      }
      const idsFilteredList = idsList.filter((id) => Number(id) !== filterItem);
      if (idsFilteredList.length === 0) {
        setParams((prevParams) => {
          const updatedParams = { ...prevParams };
          delete updatedParams[filterName];

          return updatedParams;
        });
        return;
      }

      setParams({ ...params, [filterName]: idsFilteredList.join(',') });
      return;
    }
  };

  const resetFilter = () => {
    setParams({});
  };

  const closeDropdown = () => {
    setSearchKeyword('');
  };

  useEffect(() => {
    history.push(`/essay-answers?${new URLSearchParams(params).toString()}`);
  }, [params]);

  useEffect(() => {
    window.addEventListener('click', closeDropdown);

    return () => window.removeEventListener('click', closeDropdown);
  }, []);

  return (
    <Container>
      <FilterContainer>
        {filterKeyword &&
          filterKeyword.map((item, index) => {
            return (
              <div key={index} onClick={(event) => event.stopPropagation()}>
                <button
                  onClick={() => {
                    setSearchKeyword(item);
                  }}
                >
                  {item}
                </button>
                {searchKeyword === item && (
                  <DropdownMenu css={DropdownStyle}>
                    <RoadmapSelectedFilter
                      itemName={item}
                      data={filterData[item]}
                      handleFilter={handleFilter}
                    />
                  </DropdownMenu>
                )}
              </div>
            );
          })}
      </FilterContainer>
      {Object.keys(params).length !== 0 && <button onClick={resetFilter}>필터 초기화 ⟳</button>}
    </Container>
  );
};

export default RoadmapFilter;
