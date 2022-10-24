import { useState } from 'react';
import TopKeywordList from '../../components/TopKeywordList/TopKeywordList';
import { KeywordResponse } from '../../models/Keywords';
import KeywordDetailSideSheet from '../../components/KeywordDetailSideSheet/KeywordDetailSideSheet';
import KeywordList from '../../components/KeywordList/KeywordList';
import * as Styled from './styles';
import SessionList from '../../components/SessionList/SessionList';

const RoadmapPage = () => {
  const [isSideSheetOpen, setIsSideSheetOpen] = useState(false);
  const [selectedSessionId, setSelectedSessionId] = useState(1);
  const [selectedTopKeyword, setSelectedTopKeyword] = useState<KeywordResponse | null>(null);
  const [keywordDetail, setKeywordDetail] = useState<KeywordResponse | null>(null);

  const updateSelectedTopKeyword = (keyword: KeywordResponse) => {
    setSelectedTopKeyword(keyword);
  };

  const handleClickTopKeyword = (keyword: KeywordResponse) => {
    setSelectedTopKeyword(keyword);
  };

  const handleClickSession = (id: number) => {
    setSelectedSessionId(id);
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
          <h2>세션</h2>
          <SessionList
            selectedSessionId={selectedSessionId}
            handleClickSession={handleClickSession}
          />
        </section>

        <section>
          <h2>키워드</h2>
          <TopKeywordList
            sessionId={selectedSessionId}
            selectedTopKeyword={selectedTopKeyword}
            handleClickTopKeyword={handleClickTopKeyword}
            updateSelectedTopKeyword={updateSelectedTopKeyword}
          />
        </section>

        {selectedTopKeyword && (
          <KeywordList
            handleClickKeyword={handleClickKeyword}
            selectedTopKeyword={selectedTopKeyword}
            sessionId={selectedSessionId}
          />
        )}
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
