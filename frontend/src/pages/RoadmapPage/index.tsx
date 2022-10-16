/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useState } from 'react';
import { SideSheet } from '../../components/@shared/SideSheet/SideSheet';
import ResponsiveButton from '../../components/Button/ResponsiveButton';
import KeywordSection from '../../components/KeywordSection/KeywordSection';
import { useGetSessions } from '../../hooks/queries/session';
import { getFlexStyle } from '../../styles/flex.styles';
import COLOR from '../../constants/color';

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

const RoadmapPage = () => {
  const [isSideSheetOpen, setIsSideSheetOpen] = useState(false);
  const [selectedSessionId, setSelectedSessionId] = useState(1);
  const { data: sessions } = useGetSessions();

  const handleClickSession = (id: number) => {
    setSelectedSessionId(id);
  };

  const handleOpenSideSheet = () => {
    setIsSideSheetOpen(true);
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
            <KeywordSection sessionId={selectedSessionId} />
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
              onClick={handleOpenSideSheet}
              text="JavaScript"
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
              <div
                css={[
                  getFlexStyle({
                    flexGrow: 1,
                    flexDirection: 'row',
                  }),
                ]}
              >
                <ResponsiveButton
                  onClick={handleOpenSideSheet}
                  text="비동기"
                  color="#fff"
                  backgroundColor="#8DBFE9"
                  height="50px"
                />
              </div>
              <div
                css={[
                  getFlexStyle({
                    flexGrow: 1,
                    flexDirection: 'row',
                    rowGap: '30px',
                  }),
                ]}
              >
                <ResponsiveButton
                  onClick={handleOpenSideSheet}
                  text="Callback"
                  color="#fff"
                  backgroundColor="#B8D8EA"
                  height="50px"
                />
              </div>
            </div>
          </section>
        </article>
      </main>
      {isSideSheetOpen && <SideSheet handleCloseSideSheet={handleCloseSideSheet}>hello</SideSheet>}
    </>
  );
};

export default RoadmapPage;
