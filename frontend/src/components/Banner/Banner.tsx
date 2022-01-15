/** @jsxImportSource @emotion/react */

import React from 'react';
import {
  bannerWrapperStyle,
  bannerInnerWrapperStyle,
  bannerTextAreaStyle,
  getBannerThemeByBgColor,
  getBannerSideImageStyle,
} from './Banner.styles';

export type BannerType = {
  backgroundColor?: string;
  sideImageUrl?: string;
  textContents: JSX.Element;
  reverse?: boolean;
  showMoreLink?: string;
  showMoreLinkText?: string;
  backgroundImage?: string;
  sideImagePadding?: number; // 단위 (rem)
};

interface BannerProps {
  backgroundColor?: string;
  sideImageUrl?: string;
  textContents?: JSX.Element;
  reverse?: boolean;
  showMoreLink?: string;
  showMoreLinkText?: string;
  backgroundImage?: string;
  sideImagePadding?: number;
}

const Banner = ({
  backgroundColor,
  sideImageUrl,
  textContents,
  reverse,
  showMoreLink,
  showMoreLinkText = '더보기',
  backgroundImage,
  sideImagePadding,
}: BannerProps) => {
  return (
    <div css={[bannerWrapperStyle, getBannerThemeByBgColor(backgroundColor, backgroundImage)]}>
      <div css={bannerInnerWrapperStyle}>
        {!reverse && sideImageUrl && (
          <div css={getBannerSideImageStyle(sideImageUrl, sideImagePadding, reverse)} />
        )}
        <div css={bannerTextAreaStyle}>
          {textContents}
          {showMoreLink && (
            <a href={showMoreLink} target="_blank" rel="noreferrer noopener">
              {showMoreLinkText}
            </a>
          )}
        </div>
        {reverse && sideImageUrl && (
          <div css={getBannerSideImageStyle(sideImageUrl, sideImagePadding, reverse)} />
        )}
      </div>
    </div>
  );
};

export default React.memo(Banner);
