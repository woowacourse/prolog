/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { SideSheet } from '../../components/@shared/SideSheet/SideSheet';
import ResponsiveButton from '../../components/Button/ResponsiveButton';
import KeywordSection from '../../components/KeywordSection/KeywordSection';
import { getFlexStyle } from '../../styles/flex.styles';

const Size = css`
  width: 700px;
`;

const ButtonWidth = css`
  width: fit-content;
`;

const RoadmapPage = () => {
  return (
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
          <div css={[ButtonWidth]}>
            <ResponsiveButton
              text="프론트엔드 세션1"
              color="#fff"
              backgroundColor="#4490c4"
              height="36px"
            />
          </div>
        </section>
        <section>
          <h2>키워드</h2>
          <KeywordSection />
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
  );
};

export default RoadmapPage;
