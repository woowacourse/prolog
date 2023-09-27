import { useState } from 'react';
import { KeywordResponse } from '../../models/Keywords';
import KeywordDetailSideSheet from '../../components/KeywordDetailSideSheet/KeywordDetailSideSheet';
import * as Styled from './styles';
import CurriculumList from '../../components/CurriculumList/CurriculumList';
import Roadmap from './components/Roadmap/Roadmap';
import { useRoadmap } from '../../hooks/queries/keywords';
import RoadmapStyles from './RoadmapStyles';
import { useGetCurriculums } from '../../hooks/queries/curriculum';

const lastSeenCurriculumId = Number(localStorage.getItem('curriculumId') ?? 1);

const getHueHeuristically = (curriculumName: string) => {
  const defaultHue = 0;
  const [hue] = ([
    [30, '백엔드'],
    [220, '프론트엔드'],
    [130, '안드로이드'],
  ] as const).find(([, searchName]) => curriculumName.includes(searchName)) ?? [defaultHue];

  return hue;
};

const RoadmapPage = () => {
  const [isSideSheetOpen, setIsSideSheetOpen] = useState(false);
  const { curriculums } = useGetCurriculums();
  const [selectedCurriculumId, setSelectedCurriculumId] = useState(lastSeenCurriculumId);
  const [keywordDetail, setKeywordDetail] = useState<KeywordResponse | null>(null);
  const { data: roadmap } = useRoadmap({ curriculumId: selectedCurriculumId });

  const selectedCurriculum = curriculums?.find(it => it.id === selectedCurriculumId) ?? null;

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
          {curriculums && <CurriculumList
            curriculums={curriculums}
            selectedCurriculumId={selectedCurriculumId}
            onCurriculumClick={handleClickCurriculum}
          />}
        </section>

        <section style={{ marginBottom: '4rem' }}>
          <Styled.Title>로드맵!!</Styled.Title>
          <Styled.RoadmapContainer>
            {roadmap && selectedCurriculum && (
              <Roadmap width={1040} keywords={roadmap.data} hue={getHueHeuristically(selectedCurriculum.name)} onClick={handleClickKeyword} />
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
