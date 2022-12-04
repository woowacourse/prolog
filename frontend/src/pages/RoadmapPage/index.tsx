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
          <Styled.Title>세션</Styled.Title>
          <SessionList
            selectedSessionId={selectedSessionId}
            handleClickSession={handleClickSession}
          />
        </section>

        <section>
          <Styled.Title>상위 키워드 선택</Styled.Title>
          <TopKeywordList
            sessionId={selectedSessionId}
            selectedTopKeyword={selectedTopKeyword}
            handleClickTopKeyword={handleClickTopKeyword}
            updateSelectedTopKeyword={updateSelectedTopKeyword}
          />
        </section>

        <section>
          <Styled.Title>하위 키워드 보기</Styled.Title>
          {selectedTopKeyword && (
            <KeywordList
              handleClickKeyword={handleClickKeyword}
              selectedTopKeyword={selectedTopKeyword}
              sessionId={selectedSessionId}
            />
          )}
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
