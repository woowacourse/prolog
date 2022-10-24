/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import { SideSheet } from '../../components/@shared/SideSheet/SideSheet';
import ResponsiveButton from '../../components/Button/ResponsiveButton';
import KeywordSection from '../../components/KeywordSection/KeywordSection';
import { useGetSessions } from '../../hooks/queries/session';
import { getFlexStyle } from '../../styles/flex.styles';
import COLOR from '../../constants/color';
import { useGetChildrenKeywordList } from '../../hooks/queries/keywords';
import { KeywordResponse } from '../../models/Keywords';
import KeywordDetailSideSheet from '../../components/KeywordDetailSideSheet/KeywordDetailSideSheet';

const Size = css`
  width: 700px;
`;

const ButtonStyle = css`
  width: fit-content;
  margin: 8px 0;
`;

const SessionButtonStyle = css`
  width: fit-content;
  margin: 4px 0;
`;

// 키워드 눌렀을 때  사이드바 뜨도록 하기

const RoadmapPage = () => {
  const [isSideSheetOpen, setIsSideSheetOpen] = useState(false);
  const [selectedSessionId, setSelectedSessionId] = useState(1);
  const { data: sessions } = useGetSessions();
  const [selectedTopKeyword, setSelectedTopKeyword] = useState<KeywordResponse | null>(null);
  const [keywordDetail, setKeywordDetail] = useState<KeywordResponse | null>(null);

  const { childrenKeywordList, refetchChildrenKeywordList } = useGetChildrenKeywordList({
    sessionId: selectedSessionId,
    keywordId: selectedTopKeyword?.keywordId || 1,
  });

  const updateSelectedTopKeyword = (keyword: KeywordResponse) => {
    setSelectedTopKeyword(keyword);
  };

  const handleClickTopKeyword = (keyword: KeywordResponse) => {
    setSelectedTopKeyword(keyword);
  };

  useEffect(() => {
    refetchChildrenKeywordList();
  }, [selectedTopKeyword?.keywordId]);

  const handleClickSession = (id: number) => {
    setSelectedSessionId(id);
  };

  const handleOpenSideSheet = (keyword: KeywordResponse | null) => {
    setKeywordDetail(keyword);
    setIsSideSheetOpen(true);
    console.log('@keywordDetail', keyword, keywordDetail);
  };

  const handleCloseSideSheet = () => {
    setIsSideSheetOpen(false);
  };

  return (
    <>
      <main
        css={[
          getFlexStyle({
            justifyContent: 'center',
          }),
          { marginTop: '25px' },
        ]}
      >
        <article
          css={[
            [
              Size,
              getFlexStyle({
                flexDirection: 'column',
                rowGap: '30px',
              }),
            ],
          ]}
        >
          <section>
            <h2>세션</h2>
            <div css={[getFlexStyle({ flexWrap: 'wrap', columnGap: '8px' })]}>
              {sessions?.map(({ id, name }) => {
                return (
                  <div key={id} css={[SessionButtonStyle]}>
                    <ResponsiveButton
                      onClick={() => handleClickSession(id)}
                      text={name}
                      color={selectedSessionId === id ? COLOR.WHITE : COLOR.BLACK_600}
                      backgroundColor={
                        selectedSessionId === id ? COLOR.LIGHT_BLUE_900 : COLOR.LIGHT_GRAY_400
                      }
                      height="36px"
                    />
                  </div>
                );
              })}
            </div>
          </section>
          <section>
            <h2>키워드</h2>
            <KeywordSection
              sessionId={selectedSessionId}
              selectedTopKeyword={selectedTopKeyword}
              handleClickTopKeyword={handleClickTopKeyword}
              updateSelectedTopKeyword={updateSelectedTopKeyword}
            />
          </section>

          <section
            css={[
              getFlexStyle({
                flexDirection: 'column',
                rowGap: '16px',
              }),
            ]}
          >
            <ResponsiveButton
              onClick={() => handleOpenSideSheet(selectedTopKeyword)}
              text={selectedTopKeyword?.name}
              color="#fff"
              backgroundColor="#579bca"
              height="50px"
            />
            <div
              css={[
                getFlexStyle({
                  flexDirection: 'row',
                  columnGap: '30px',
                }),
              ]}
            >
              {childrenKeywordList?.map((keyword) => {
                return (
                  <>
                    <div
                      css={[
                        getFlexStyle({
                          flexGrow: 1,
                          flexDirection: 'row',
                        }),
                      ]}
                    >
                      <ResponsiveButton
                        onClick={() => handleOpenSideSheet(keyword)}
                        text={keyword.name}
                        color="#fff"
                        backgroundColor="#8DBFE9"
                        height="50px"
                      />
                    </div>
                    <div
                      css={[
                        getFlexStyle({
                          flexGrow: 1,
                          flexDirection: 'column',
                          rowGap: '10px',
                        }),
                      ]}
                    >
                      {keyword.childrenKeywords?.map((keyword) => {
                        return (
                          <ResponsiveButton
                            onClick={() => handleOpenSideSheet(keyword)}
                            text={keyword.name}
                            color="#fff"
                            backgroundColor="#B8D8EA"
                            height="50px"
                          />
                        );
                      })}
                    </div>
                  </>
                );
              })}
            </div>
          </section>
        </article>
      </main>
      {isSideSheetOpen && keywordDetail && (
        <KeywordDetailSideSheet
          keywordDetail={keywordDetail}
          sessionId={selectedSessionId}
          handleCloseSideSheet={handleCloseSideSheet}
        />
      )}
    </>
  );
};

export default RoadmapPage;
