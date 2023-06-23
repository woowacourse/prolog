/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';
import { ReactComponent as PauseIcon } from '../../assets/images/pause.svg';
import { ReactComponent as PlayIcon } from '../../assets/images/play.svg';
import Banner, { BannerType } from './Banner';
import {
  BannerControllerWrapperStyle,
  BannerSliderItemStyle,
  BannerSliderWrapperStyle,
  getBannerControllerItemStyle,
  getNextButtonStyle,
  getPlayButtonStyle,
  getPrevButtonStyle,
  SelectedBannerControllerItemStyle
} from './BannerList.styles';


const DEFAULT_BANNER_INDEX = 0;
const BANNER_PLAY_INTERVAL = 3000;

const BannerList = ({ bannerList = [] }: { bannerList: BannerType[] }): JSX.Element => {
  const [currentBannerIndex, setCurrentBannerIndex] = useState(0);
  const currentBannerBackgroundColor = bannerList[currentBannerIndex].backgroundColor;

  const bannerSwitchIntervalId = useRef<NodeJS.Timeout>();
  const [mouseOnBanner, setMouseOnBanner] = useState(false);
  const [isBannerPlaying, setIsBannerPlaying] = useState(true);

  const next = () => {
    if (currentBannerIndex + 1 >= bannerList.length) {
      setCurrentBannerIndex(DEFAULT_BANNER_INDEX);
      return;
    }

    setCurrentBannerIndex(currentBannerIndex + 1);
  };

  const prev = () => {
    if (currentBannerIndex - 1 < 0) {
      setCurrentBannerIndex(bannerList.length - 1);
      return;
    }
    setCurrentBannerIndex(currentBannerIndex - 1);
  };

  useEffect(() => {
    if (isBannerPlaying && mouseOnBanner === false) {
      bannerSwitchIntervalId.current = setTimeout(next, BANNER_PLAY_INTERVAL);
      return;
    }

    if (mouseOnBanner) {
      clearTimeout(bannerSwitchIntervalId.current as NodeJS.Timeout);
      return;
    }

    return () => {
      clearTimeout(bannerSwitchIntervalId.current as NodeJS.Timeout);
    };
  }, [currentBannerIndex, mouseOnBanner, isBannerPlaying]);

  useEffect(() => {
    return () => {
      clearTimeout(bannerSwitchIntervalId.current as NodeJS.Timeout);
    };
  }, []);

  return (
    <div
      css={BannerSliderWrapperStyle}
      onMouseEnter={() => setMouseOnBanner(true)}
      onMouseLeave={() => setMouseOnBanner(false)}
      tabIndex={1}
    >
      {bannerList.map(
        (
          {
            backgroundColor,
            sideImageUrl,
            textContents,
            reverse,
            showMoreLink,
            showMoreLinkText,
            backgroundImage,
            sideImagePadding,
          },
          index
        ) => (
          <div
            key={`bannerList-${index}`}
            css={[
              BannerSliderItemStyle,
              css`
                right: calc(100% * ${currentBannerIndex - index});
              `,
            ]}
          >
            <Banner
              backgroundColor={backgroundColor}
              sideImageUrl={sideImageUrl}
              sideImagePadding={sideImagePadding}
              textContents={textContents}
              reverse={reverse}
              showMoreLink={showMoreLink}
              showMoreLinkText={showMoreLinkText}
              backgroundImage={backgroundImage}
            />
          </div>
        )
      )}

      <div>
        <div css={BannerControllerWrapperStyle}>
          {Array.from({ length: bannerList.length }).map((_, index) => (
            <button
              key={`banner-${index}-button`}
              type="button"
              css={[
                getBannerControllerItemStyle(currentBannerBackgroundColor),
                index === currentBannerIndex ? SelectedBannerControllerItemStyle : null,
              ]}
              onClick={() => setCurrentBannerIndex(index)}
              aria-label={`${index + 1}번 째 배너로 이동`}
            />
          ))}
          <button
            type="button"
            css={getPlayButtonStyle(currentBannerBackgroundColor)}
            onClick={() => setIsBannerPlaying((isBannerPlaying) => !isBannerPlaying)}
          >
            {isBannerPlaying ? <PauseIcon /> : <PlayIcon />}
          </button>
        </div>
      </div>

      {mouseOnBanner && (
        <>
          <button css={getPrevButtonStyle(currentBannerBackgroundColor)} onClick={prev}>
            <span>이전 배너</span>
          </button>

          <button css={getNextButtonStyle(currentBannerBackgroundColor)} onClick={next}>
            <span>다음 배너</span>
          </button>
        </>
      )}
    </div>
  );
};

export default BannerList;
