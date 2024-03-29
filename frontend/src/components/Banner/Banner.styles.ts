import { css } from '@emotion/react';
import { COLOR } from '../../constants';
import MEDIA_QUERY from '../../constants/mediaQuery';
import { getTextColor } from '../../utils/textColorPicker';

export const bannerWrapperStyle = css`
  width: 100%;
  height: 320px;

  overflow: hidden;

  strong,
  h2 {
    font-family: 'BMHANNAPro';
    font-weight: normal;
  }

  strong {
    font-size: 2.4rem;
  }

  a {
    max-width: 18rem;

    padding: 0.8rem 2rem;

    border-radius: 2rem;

    text-align: center;
  }

  ${MEDIA_QUERY.xs} {
    height: 180px;

    br {
      display: none;
    }

    strong {
      font-size: 2rem;
    }
  }
`;

export const bannerInnerWrapperStyle = css`
  max-width: calc(100% - 60rem);
  height: 100%;
  margin: 0 auto;

  display: flex;
  justify-content: center;
  align-items: center;

  ${MEDIA_QUERY.xs} {
    max-width: calc(100% - 5rem);
  }
`;

export const bannerTextAreaStyle = css`
  height: 100%;

  padding-right: 6rem;

  display: flex;
  flex-direction: column;
  justify-content: center;

  * {
    margin: 0;
    line-height: 1.25;
  }

  h2 {
    font-size: 6rem;
  }

  p {
    font-size: 2rem;
    line-height: 1.5;
  }

  ${MEDIA_QUERY.xs} {
    padding-right: 0;

    h2 {
      font-size: 3rem;
    }

    p {
      font-size: 1.5rem;
      line-height: 1.5;
    }
  }
`;

export const getBannerThemeByBgColor = (
  backgroundColor = COLOR.WHITE,
  backgroundImage?: string
) => css`
  background-color: ${backgroundColor};
  ${backgroundImage &&
  css`
    background: url(${backgroundImage});
    background-repeat: no-repeat;
    background-size: cover;
  `};
  color: ${getTextColor(backgroundColor)};

  h2,
  p {
    color: inherit;
  }

  a {
    margin-top: 1rem;

    background-color: transparent;
    border: 1px solid ${getTextColor(backgroundColor)};
    color: ${getTextColor(backgroundColor)};

    :active,
    :hover {
      background-color: ${getTextColor(backgroundColor)};
      color: ${backgroundColor};
    }
  }
`;

export const getBannerSideImageStyle = (sideImageUrl, sideImagePadding, reverse) => css`
  width: calc(320px - ${sideImagePadding ?? 0 * 2}rem);
  height: calc(320px - ${sideImagePadding ?? 0 * 2}rem);

  background-image: url(${sideImageUrl});
  background-repeat: no-repeat;
  background-size: cover;

  ${reverse ? 'margin-left: 3rem;' : 'margin-right: 3rem;'}

  ${MEDIA_QUERY.xs} {
    width: 0;
    height: 0;
    margin: 0;
  }
`;
