import { useState } from 'react';
import { KeywordResponse } from '../../models/Keywords';
import KeywordDetailSideSheet from '../../components/KeywordDetailSideSheet/KeywordDetailSideSheet';
import * as Styled from './styles';
import CurriculumList from '../../components/CurriculumList/CurriculumList';
import Roadmap from './Roadmap';
import { useRoadmap } from '../../hooks/queries/keywords';
import RoadmapStyles from './RoadmapStyles';

const lastSeenCurriculumId = Number(localStorage.getItem('curriculumId') ?? 1);

const RoadmapPage = () => {
  const [isSideSheetOpen, setIsSideSheetOpen] = useState(false);
  const [selectedCurriculumId, setSelectedCurriculumId] = useState(lastSeenCurriculumId);
  const [keywordDetail, setKeywordDetail] = useState<KeywordResponse | null>(null);

  const { data: roadmap } = useRoadmap({ curriculumId: selectedCurriculumId });

  const handleClickCurriculum = (id: number) => {
    setSelectedCurriculumId(id);
    localStorage.setItem('curriculumId', String(id));
  };

  const handleClickKeyword = (keyword: KeywordResponse | null) => {
    setKeywordDetail(keyword);
    setIsSideSheetOpen(true);
  };

  const handleCloseSideSheet = () => {
    setIsSideSheetOpen(false);
  };

  return (
    <Styled.Root>
      <RoadmapStyles />
      <Styled.Main>
        <section>
          <Styled.Title>커리큘럼</Styled.Title>
          <CurriculumList
            selectedCurriculumId={selectedCurriculumId}
            handleClickCurriculum={handleClickCurriculum}
          />
        </section>

        <section style={{ marginBottom: '4rem' }}>
          <Styled.Title>로드맵!!</Styled.Title>
          <Styled.RoadmapContainer>
            {roadmap && (
              <Roadmap width={1040} keywords={roadmap.data} onClick={handleClickKeyword} />
            )}
          </Styled.RoadmapContainer>
        </section>
      </Styled.Main>

      {isSideSheetOpen && keywordDetail && (
        <KeywordDetailSideSheet
          keywordDetail={keywordDetail}
          handleCloseSideSheet={handleCloseSideSheet}
        />
      )}
    </Styled.Root>
  );
};

export default RoadmapPage;
