import { useState } from 'react';
import { KeywordResponse } from '../../models/Keywords';
import KeywordDetailSideSheet from '../../components/KeywordDetailSideSheet/KeywordDetailSideSheet';
import * as Styled from './styles';
import Roadmap from './components/Roadmap/Roadmap';
import { useRoadmap } from '../../hooks/queries/keywords';
import RoadmapStyles from './RoadmapStyles';
import { useGetCurriculums } from '../../hooks/queries/curriculum';
import ImportanceLegend from './components/ImportanceLegend/ImportanceLegend';
import ResponsiveButton from '../../components/Button/ResponsiveButton';
import { COLOR } from '../../constants';
import { Link } from 'react-router-dom';

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

  const selectedCurriculum = curriculums?.find((it) => it.id === selectedCurriculumId) ?? null;

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
          <Styled.RoadmapHeader>
            <Styled.CurriculumButtonList>
              {curriculums?.map((curriculum) => (
                <ResponsiveButton
                  style={{ width: 'fit-content' }}
                  onClick={() => handleClickCurriculum(curriculum.id)}
                  text={curriculum.name}
                  color={selectedCurriculumId === curriculum.id ? COLOR.WHITE : COLOR.BLACK_600}
                  backgroundColor={
                    selectedCurriculumId === curriculum.id
                      ? `hsl(${getHueHeuristically(curriculum.name)}, 50%, 40%)`
                      : COLOR.LIGHT_GRAY_400
                  }
                  height="32px"
                />
              ))}
            </Styled.CurriculumButtonList>
            {selectedCurriculum && <Link to={`/essay-answers?curriculumId=${selectedCurriculum.id}`}>전체 답변 보러가기</Link>}
          </Styled.RoadmapHeader>
        </section>

        <section style={{ marginBottom: '4rem' }}>
          <Styled.Title>로드맵!!</Styled.Title>

          <ImportanceLegend />

          <Styled.RoadmapContainer>
            {roadmap && selectedCurriculum && (
              <Roadmap
                width={1040}
                keywords={roadmap.data}
                hue={getHueHeuristically(selectedCurriculum.name)}
                onClick={handleClickKeyword}
              />
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
