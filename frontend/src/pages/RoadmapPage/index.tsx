import { useState } from 'react';
import TopKeywordList from '../../components/TopKeywordList/TopKeywordList';
import { KeywordResponse } from '../../models/Keywords';
import KeywordDetailSideSheet from '../../components/KeywordDetailSideSheet/KeywordDetailSideSheet';
import KeywordList from '../../components/KeywordList/KeywordList';
import * as Styled from './styles';
import SessionList from '../../components/SessionList/SessionList';
import CurriculumList from '../../components/CurriculumList/CurriculumList';

const lastSeenCurriculumId = Number(localStorage.getItem('curriculumId') ?? 1);

const RoadmapPage = () => {
  const [isSideSheetOpen, setIsSideSheetOpen] = useState(false);
  const [selectedCurriculumId, setSelectedCurriculumId] = useState(lastSeenCurriculumId);
  const [selectedSessionId, setSelectedSessionId] = useState(-1);
  const [selectedTopKeyword, setSelectedTopKeyword] = useState<KeywordResponse | null>(null);
  const [keywordDetail, setKeywordDetail] = useState<KeywordResponse | null>(null);

  const handleClickCurriculum = (id: number) => {
    setSelectedCurriculumId(id);
    localStorage.setItem('curriculumId', String(id));
  };

  const handleClickSession = (sessionId: number) => {
    setSelectedSessionId(sessionId);
  };

  const handleClickTopKeyword = (keyword: KeywordResponse) => {
    setSelectedTopKeyword(keyword);
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
      <Styled.Main>
        <section>
          <Styled.Title>커리큘럼</Styled.Title>
          <CurriculumList
            selectedCurriculumId={selectedCurriculumId}
            handleClickCurriculum={handleClickCurriculum}
          />
        </section>

        <section>
          <Styled.Title>세션</Styled.Title>
          <SessionList
            curriculumId={selectedCurriculumId}
            selectedSessionId={selectedSessionId}
            handleClickSession={handleClickSession}
          />
        </section>

        <section>
          <Styled.Title>상위 키워드 선택</Styled.Title>
          {selectedSessionId !== -1 ? (
            <TopKeywordList
              sessionId={selectedSessionId}
              selectedTopKeyword={selectedTopKeyword}
              handleClickTopKeyword={handleClickTopKeyword}
            />
          ) : null}
        </section>

        <section style={{marginBottom: "4rem"}}>
          <Styled.Title>하위 키워드 보기</Styled.Title>
          {selectedSessionId !== -1 && selectedTopKeyword ? (
            <KeywordList
              sessionId={selectedSessionId}
              selectedTopKeyword={selectedTopKeyword}
              handleClickKeyword={handleClickKeyword}
            />
          ) : null}
        </section>
      </Styled.Main>

      {isSideSheetOpen && keywordDetail && (
        <KeywordDetailSideSheet
          keywordDetail={keywordDetail}
          sessionId={selectedSessionId}
          handleCloseSideSheet={handleCloseSideSheet}
        />
      )}
    </Styled.Root>
  );
};

export default RoadmapPage;
