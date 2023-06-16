import { css } from '@emotion/react';
import { COLOR } from '../../constants';
import MEDIA_QUERY from '../../constants/mediaQuery';
import { getTextColor } from '../../utils/textColorPicker';

export const BannerSliderWrapperStyle = css`
  width: 100%;
  height: 320px;

  position: relative;
  overflow: hidden;

  box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.2);

  ${MEDIA_QUERY.xs} {
    height: 180px;
  }
`;

export const BannerSliderItemStyle = css`
  position: absolute;
  top: 0;
  transition: right 0.6s ease-out;

  width: 100%;
`;

export const BannerControllerWrapperStyle = css`
  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  bottom: 1rem;
  left: 50%;

  transform: translateX(-50%);

  > *:not(:last-child) {
    margin-right: 1rem;
  }
`;

export const getBannerControllerItemStyle = (backgroundColor = COLOR.WHITE) => css`
  width: 1rem;
  height: 1rem;

  background-color: ${getTextColor(backgroundColor)};
  border-radius: 1rem;

  opacity: 0.4;
  transition: width 0.3s, height 0.1s;

  cursor: pointer;
`;

export const SelectedBannerControllerItemStyle = css`
  width: 2rem;
  height: 1.2rem;

  opacity: 1; ;
`;

export const ControllerButtonStyle = css`
  width: 1.6rem;
  height: 1.6rem;

  position: relative;

  border-radius: 1rem;

  span {
    width: 0;
    display: inline-block;
    overflow: hidden;
  }
`;

export const getPauseButtonStyle = (backgroundColor = COLOR.WHITE) => {
  const iconColor = getTextColor(backgroundColor) === COLOR.WHITE ? COLOR.BLACK_900 : COLOR.WHITE;

  return css`
    ${ControllerButtonStyle}
    background-color: ${getTextColor(backgroundColor)};

    :after {
      width: 0.1rem;
      height: 0.8rem;

      content: ' ';
      position: absolute;
      top: 50%;
      left: 50%;

      transform: translateX(-50%) translateY(-50%);

      border-right: 3px solid ${iconColor};
      border-left: 3px solid ${iconColor};
    }
  `;
};

export const getPlayButtonsStyle = (backgroundColor = COLOR.WHITE) => {
  const iconColor = getTextColor(backgroundColor) === COLOR.WHITE ? COLOR.BLACK_900 : COLOR.WHITE;

  return css`
    ${ControllerButtonStyle}

    background-color: ${getTextColor(backgroundColor)};

    :after {
      width: 0;
      height: 0;

      content: ' ';
      position: absolute;
      top: 0;
      left: 0;

      transform: translateX(60%) translateY(30%);

      border-top: 0.5rem solid transparent;
      border-bottom: 0.5rem solid transparent;
      border-right: 0.5rem solid transparent;
      border-left: 0.5rem solid ${iconColor};
    }
  `;
};

export const BannerNavigationButton = css`
  width: 4.8rem;
  height: 100%;

  background-color: transparent;

  position: absolute;
  top: 0;

  z-index: 10;

  span {
    width: 0;
    display: inline-block;
    overflow: hidden;
  }
`;

export const getPrevButtonStyle = (backgroundColor = COLOR.WHITE) => css`
  ${BannerNavigationButton}

  left: 0;

  :after {
    content: ' ';
    position: absolute;
    top: 50%;
    left: 50%;

    transform: translateX(-50%) translateY(-50%);

    border-top: 1.5rem solid transparent;
    border-bottom: 1.5rem solid transparent;
    border-right: 1.5rem solid ${getTextColor(backgroundColor)};
    border-left: 1.5rem solid transparent;
  }
`;

export const getNextButtonStyle = (backgroundColor = COLOR.WHITE) => css`
  ${BannerNavigationButton}
  right: 0;

  :after {
    content: ' ';
    position: absolute;
    top: 50%;
    left: 50%;

    transform: translateX(-50%) translateY(-50%);

    border-top: 1.5rem solid transparent;
    border-bottom: 1.5rem solid transparent;
    border-left: 1.5rem solid ${getTextColor(backgroundColor)};
    border-right: 1.5rem solid transparent;
  }
`;
