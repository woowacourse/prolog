import { useEffect, useState } from 'react';
import DropdownMenu from '../../../../components/DropdownMenu/DropdownMenu';
import { useGetQuizzes } from '../../../../hooks/queries/essayanswer';
import { useGetMembers } from '../../../../hooks/queries/filters';
import { useRoadmap } from '../../../../hooks/queries/keywords';
import { KeywordResponse } from '../../../../models/Keywords';
import { Container, DropdownStyle, FilterContainer } from './RoadmapFilter.styles';

const filterKoreanNames: Record<string, string> = {
  keywordId: '주제',
  memberIds: '회원',
  quizIds: '질문',
}

const useGetKeywords = ({ curriculumId }: { curriculumId: number }) => {
  const { data: roadmap } = useRoadmap({ curriculumId });

  const extractKeywords = (
    keywordResponse: KeywordResponse
  ): Pick<KeywordResponse, 'keywordId' | 'name'>[] => {
    return [
      { keywordId: keywordResponse.keywordId, name: keywordResponse.name },
      ...keywordResponse.childrenKeywords.map(extractKeywords).flat(),
    ];
  };

  return roadmap?.data.map(extractKeywords).flat() ?? [];
};

interface RoadmapFilterProps {
  curriculumId: number;
  filter: Record<string, string>;
  onFilterChange: (filter: Record<string, string>) => void;
}

const RoadmapFilter = ({ curriculumId, filter, onFilterChange }: RoadmapFilterProps) => {
  const [activeFilterKeyword, setActiveFilterKeyword] = useState<string | null>(null);

  const keywords = useGetKeywords({ curriculumId });
  const { data: quizzes } = useGetQuizzes({ curriculumId });
  const { data: members } = useGetMembers();

  const filterData: Record<string, Array<{ key: string; label: string }>> = {
    keywordId: keywords.map((keyword) => ({ key: String(keyword.keywordId), label: keyword.name })),
    memberIds: members?.map((member) => ({ key: String(member.id), label: member.nickname })) ?? [],
    quizIds: quizzes?.map((quiz) => ({ key: String(quiz.id), label: quiz.question })) ?? [],
  };

  const handleFilter = (filterName: string, filterItemKey: string) => {
    if (filterName === 'curriculumId' || filterName === 'keywordId') {
      if (!filter[filterName] || filter[filterName] !== filterItemKey) {
        onFilterChange({ ...filter, [filterName]: filterItemKey });
        return;
      }

      if (filter[filterName] === filterItemKey) {
        const updatedFilter = { ...filter };
        delete updatedFilter[filterName];

        onFilterChange(updatedFilter);
        return;
      }
    }

    if (filterName === 'quizIds' || filterName === 'memberIds') {
      if (!filter[filterName]) {
        onFilterChange({ ...filter, [filterName]: String(filterItemKey) });
        return;
      }
      const idsList = filter[filterName].split(',');
      if (!idsList.includes(String(filterItemKey))) {
        const ids = filter[filterName] + ',' + String(filterItemKey);
        onFilterChange({ ...filter, [filterName]: ids });
        return;
      }
      const idsFilteredList = idsList.filter((id) => id !== filterItemKey);
      if (idsFilteredList.length === 0) {
        const updatedFilter = { ...filter };
        delete updatedFilter[filterName];

        onFilterChange(updatedFilter);
        return;
      }

      onFilterChange({ ...filter, [filterName]: idsFilteredList.join(',') });
      return;
    }
  };

  const resetFilter = () => {
    onFilterChange({});
  };

  const closeDropdown = () => {
    setActiveFilterKeyword(null);
  };

  useEffect(() => {
    window.addEventListener('click', closeDropdown);

    return () => window.removeEventListener('click', closeDropdown);
  }, []);

  return (
    <Container>
      <FilterContainer>
        {
          Object.keys(filterData).map((filterKeyword, index) => (
            <div key={index} onClick={(event) => event.stopPropagation()}>
              <button
                onClick={() => setActiveFilterKeyword(filterKeyword)}
              >
                {filterKoreanNames[filterKeyword]}
              </button>
              {activeFilterKeyword === filterKeyword && (
                <DropdownMenu css={DropdownStyle}>
                  <ul>
                    {filterData[filterKeyword].map((item) => (
                      <li onClick={() => handleFilter(filterKeyword, item.key)}>{item.label}</li>
                    ))}
                  </ul>
                </DropdownMenu>
              )}
            </div>
          ))}
      </FilterContainer>
      {Object.keys(filter).length !== 0 && <button onClick={resetFilter}>필터 초기화 ⟳</button>}
    </Container>
  );
};

export default RoadmapFilter;
